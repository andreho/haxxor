package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class DefaultInjector
  implements AspectStepParameterInjector {
  public static final DefaultInjector INSTANCE = new DefaultInjector();

  @Override
  public boolean injectParameter(final AspectStep<?> aspectStep,
                                 final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxParameter parameter,
                                 final HxInstruction instruction) {
    HxCgenUtils.genericLoadDefault(parameter.getType(), instruction.asStream());
    return true;
  }
}
