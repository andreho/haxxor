package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArityParameterInjector
  extends AbstractParameterInjector {

  private static final Class<Arity> ARITY_ANNOTATION_TYPE = Arity.class;

  public static final ArityParameterInjector INSTANCE = new ArityParameterInjector();

  public ArityParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {
    if(!parameter.isAnnotationPresent(ARITY_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    if(!parameter.hasType("int")) {
      return NOT_INJECTABLE;
    }

    return MAX_SUITABLE;
  }

  @Override
  public void inject(final AspectAdvice<?> aspectAdvice,
                     final AspectContext context,
                     final HxMethod original,
                     final HxMethod shadow,
                     final HxMethod interceptor,
                     final HxParameter parameter,
                     final HxInstruction anchor) {
    anchor.asAnchoredStream()
          .LDC(original.getParametersCount());
  }
}
