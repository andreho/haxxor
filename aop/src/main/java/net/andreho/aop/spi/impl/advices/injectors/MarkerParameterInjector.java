package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Marker;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class MarkerParameterInjector
  extends AbstractAnnotatedParameterInjector {

  private static final String HELPERS_CLASS = "net/andreho/aop/spi/Helpers";
  public static final MarkerParameterInjector INSTANCE = new MarkerParameterInjector();

  public MarkerParameterInjector() {
    super(Marker.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction instruction) {

    final HxInstruction anchor = instruction.getNext();

    InterceptedParameterInjector
      .INSTANCE
      .checkedParameterInjection(aspectAdvice, context, interceptor, original, shadow, parameter, instruction);

    final HxExtendedCodeStream stream = anchor.getPrevious().asStream();

    stream
      .TYPE(parameter.getType())
      .INVOKESTATIC(
        HELPERS_CLASS,
        "getAnnotationOf",
        "(Ljava/lang/reflect/AnnotatedElement;Ljava/lang/Class;)Ljava/lang/annotation/Annotation;",
        false)
      .CHECKCAST(parameter.getType());

    return true;
  }
}
