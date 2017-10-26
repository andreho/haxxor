package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 06:39.
 */
public abstract class AbstractMultipleParameterInjector
  extends AbstractParameterInjector {

  private final AspectAdviceParameterInjector[] array;

  public AbstractMultipleParameterInjector(final AspectAdviceParameterInjector... array) {
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
    for (AspectAdviceParameterInjector injector : array) {
      if(injector.isInjectable(context, interceptor, original, shadow, parameter)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean injectParameter(final AspectAdvice<?> aspectAdvice,
                                 final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxParameter parameter,
                                 final HxInstruction anchor) {
    for (AspectAdviceParameterInjector injector : array) {
      if(injector.isInjectable(context, interceptor, original, shadow, parameter) &&
        injector.injectParameter(aspectAdvice, context, interceptor, original, shadow, parameter, anchor)) {
        return true;
      }
    }

    return false;
  }
}
