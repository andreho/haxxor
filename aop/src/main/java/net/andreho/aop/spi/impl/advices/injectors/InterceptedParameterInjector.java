package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class InterceptedParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String HELPERS_CLASS = "net/andreho/aop/spi/impl/advices/injectors/Helpers";
  public static final InterceptedParameterInjector INSTANCE = new InterceptedParameterInjector();

  public InterceptedParameterInjector() {
    super(Intercepted.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {
    final HxExtendedCodeStream stream = instruction.asStream();

    if (original.isStatic()) {
      stream.TYPE(original.getDeclaringMember());
    } else {
      stream.THIS().INVOKESTATIC(HELPERS_CLASS,
                                 "getClassOf",
                                 "(Ljava/lang/Object;)Ljava/lang/Class;", false);
    }

    final String key = original.toDescriptor(new StringBuilder(original.getName())).toString();

    stream.LDC(key);

    if (original.isConstructor()) {

      stream.INVOKESTATIC(HELPERS_CLASS,
                          "getConstructorOf",
                          "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Constructor;", false);
    } else {

      stream.INVOKESTATIC(HELPERS_CLASS,
                          "getMethodOf",
                          "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Method;", false);
    }

    return true;
  }
}
