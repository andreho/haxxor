package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 06:39.
 */
public abstract class AbstractMultipleInjector extends AbstractInjector {

  private final AspectStepParameterInjector[] array;

  public AbstractMultipleInjector(final AspectStepParameterInjector ... array) {
    this.array = Objects.requireNonNull(array);
    if(array.length == 0) {
      throw new IllegalArgumentException("Array must be not empty.");
    }
  }

  @Override
  public boolean isInjectable(final AspectContext context,
                              final HxMethod interceptor,
                              final HxMethod original,
                              final HxMethod shadow,
                              final HxParameter parameter) {
    for (AspectStepParameterInjector injector : array) {
      if(injector.isInjectable(context, interceptor, original, shadow, parameter)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean injectParameter(final AspectStep<?> aspectStep,
                                 final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxParameter parameter,
                                 final HxInstruction instruction) {
    for (AspectStepParameterInjector injector : array) {
      if(injector.isInjectable(context, interceptor, original, shadow, parameter) &&
        injector.injectParameter(aspectStep, context, interceptor, original, shadow, parameter, instruction)) {
        return true;
      }
    }

    return false;
  }
}
