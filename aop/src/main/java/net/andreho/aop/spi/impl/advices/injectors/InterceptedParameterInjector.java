package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class InterceptedParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String HELPERS_CLASS = "net/andreho/aop/spi/Helpers";
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
                                           final HxInstruction anchor) {

    DeclaringParameterInjector.INSTANCE
      .checkedParameterInjection(aspectAdvice, context, interceptor, original, shadow, parameter, anchor);

    String key;
    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    if (original.isConstructor()) {
      key = original.toDescriptor();
      stream
        .LDC(key)
        .INVOKESTATIC(HELPERS_CLASS,
          "getConstructorOf",
          "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Constructor;", false);
    } else {
      key = original.toDescriptor(new StringBuilder(original.getName())).toString();
      stream
        .LDC(key)
        .INVOKESTATIC(HELPERS_CLASS,
          "getMethodOf",
          "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Method;", false);
    }

    return true;
  }
}
