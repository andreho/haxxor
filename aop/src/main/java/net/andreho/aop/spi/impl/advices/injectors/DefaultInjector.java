package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class DefaultInjector
  implements AspectAdviceParameterInjector {
  public static final DefaultInjector INSTANCE = new DefaultInjector();

  @Override
  public boolean injectParameter(final AspectAdvice<?> aspectAdvice,
                                 final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxParameter parameter,
                                 final HxInstruction instruction) {
    instruction.asStream().GENERIC_DEFAULT_VALUE(parameter.getType());
    return true;
  }
}
