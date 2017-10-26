package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Original;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxHandleTag;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxMethodHandle;

import java.util.concurrent.Callable;

import static net.andreho.haxxor.cgen.HxArguments.createArguments;
import static net.andreho.haxxor.cgen.HxMethodHandle.createMethodHandle;
import static net.andreho.haxxor.cgen.HxMethodType.createMethodType;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class OriginalParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String CALLABLE_TYPE_NAME = Callable.class.getName();
  private static final String ORIGINAL_ANNOTATION_TYPE_NAME = Original.class.getName();
  public static final OriginalParameterInjector INSTANCE = new OriginalParameterInjector();

  public OriginalParameterInjector() {
    super(ORIGINAL_ANNOTATION_TYPE_NAME);
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction anchor) {

    if (parameter.isAnnotationPresent(ORIGINAL_ANNOTATION_TYPE_NAME)
      && parameter.hasType(CALLABLE_TYPE_NAME)) {
      final HxExtendedCodeStream stream = anchor.asAnchoredStream();
      final StringBuilder builder = new StringBuilder("(");
      final boolean isStatic = original.isStatic();

      if(!isStatic) {
        stream.THIS();
        original.getDeclaringType().toDescriptor(builder);
      }

      for (int i = 0, len = shadow.getParametersCount(); i < len; i++) {
        shadow.getParameterTypeAt(i).toDescriptor(builder);
      }

      builder.append(")Ljava/util/concurrent/Callable;");

      final HxMethodHandle metafactory = createMethodHandle(
        HxHandleTag.INVOKESTATIC,
        "java/lang/invoke/LambdaMetafactory",
        "metafactory",
        "(" +
          "Ljava/lang/invoke/MethodHandles$Lookup;" +
          "Ljava/lang/String;" +
          "Ljava/lang/invoke/MethodType;" +
          "Ljava/lang/invoke/MethodType;" +
          "Ljava/lang/invoke/MethodHandle;" +
          "Ljava/lang/invoke/MethodType;" +
        ")Ljava/lang/invoke/CallSite;",
        false
      );

      stream
        .GENERIC_LOAD(original.getParameterTypes(), isStatic ? 0 : 1)
        .INVOKEDYNAMIC(
          "call",
          builder.toString(),
          metafactory,
          createArguments()
            .add(
              createMethodType("()Ljava/lang/Object;")
            ).add(
              createMethodHandle(
                isStatic? HxHandleTag.INVOKESTATIC : HxHandleTag.INVOKESPECIAL,
                shadow.getDeclaringType().toInternalName(),
                shadow.getName(),
                shadow.toDescriptor(),
                shadow.getDeclaringType().isInterface()
              )
            ).add(
              createMethodType(toReturnSignature(shadow))
            )
          );

      return true;
    }

    return false;
  }

  private String toReturnSignature(final HxMethod shadow) {
    StringBuilder builder = new StringBuilder("()");

    HxType returnType = shadow.getReturnType();
    switch (returnType.getSort()) {
      case BOOLEAN: builder.append("Ljava/lang/Boolean;"); break;
      case BYTE: builder.append("Ljava/lang/Byte;"); break;
      case SHORT: builder.append("Ljava/lang/Short;"); break;
      case CHAR: builder.append("Ljava/lang/Character;"); break;
      case INT: builder.append("Ljava/lang/Integer;"); break;
      case FLOAT: builder.append("Ljava/lang/Float;"); break;
      case LONG: builder.append("Ljava/lang/Long;"); break;
      case DOUBLE: builder.append("Ljava/lang/Double;"); break;
      default:
        returnType.toDescriptor(builder);
    }
    return builder.toString();
  }
}