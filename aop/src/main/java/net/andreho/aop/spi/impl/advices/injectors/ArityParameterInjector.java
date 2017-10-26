package net.andreho.aop.spi.impl.advices.injectors;

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
  extends AbstractAnnotatedParameterInjector {
  public static final ArityParameterInjector INSTANCE = new ArityParameterInjector();

  public ArityParameterInjector() {
    super(Arity.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction anchor) {

    anchor.asAnchoredStream()
          .LDC(original.getParametersCount());
    return true;
  }
}
