package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface AspectAdviceParameterInjector {
  /**
   * Tries to answer a question about whether this injector
   * can be applied and inject valid value into the given parameter or not
   * @param context to use
   * @param interceptor to invoke
   * @param original that should be modified
   * @param shadow that should be executed
   * @param parameter that must be populated with a suitable value
   * @return
   */
  default boolean isInjectable(
    AspectContext context,
    HxMethod interceptor,
    HxMethod original,
    HxMethod shadow,
    HxParameter parameter) {

    return true;
  }

  /**
   * Allocates value for the parameter under the given index
   * @param aspectAdvice is the currently processed step
   * @param context to use
   * @param interceptor to invoke and whose parameters to populate
   * @param original that should be modified
   * @param shadow that should be executed
   * @param parameter that must be populated with a suitable value
   * @param anchor for injection
   * @return <b>true</b> if injection was successful or
   * <b>false</b> to signal that the injection value isn't available, which leads to default injection.
   */
  boolean injectParameter(
    AspectAdvice<?> aspectAdvice,
    AspectContext context,
    HxMethod interceptor,
    HxMethod original,
    HxMethod shadow,
    HxParameter parameter,
    HxInstruction anchor);

  static AspectAdviceParameterInjector with(final Collection<AspectAdviceParameterInjector> list) {
    return with(list.toArray(new AspectAdviceParameterInjector[0]));
  }

  static AspectAdviceParameterInjector with(final AspectAdviceParameterInjector... list) {
    return (aspectStep, context, interceptor, method, shadow, parameter, instruction) -> {
      for(AspectAdviceParameterInjector injector : list) {
        if(injector.injectParameter(aspectStep, context, interceptor, method, shadow, parameter, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
