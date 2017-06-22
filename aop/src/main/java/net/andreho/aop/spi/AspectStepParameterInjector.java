package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface AspectStepParameterInjector {

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
   * @param aspectStep is the currently processed step
   * @param context to use
   * @param interceptor to invoke
   * @param original that should be modified
   * @param shadow that should be executed
   * @param parameter that must be populated with a suitable value
   * @param instruction for injection   @return <b>true</b> if injection was successful or
   * <b>false</b> to signal that the injection value isn't available, which leads to default injection.
   */
  boolean injectParameter(
    AspectStep<?> aspectStep,
    AspectContext context,
    HxMethod interceptor,
    HxMethod original,
    HxMethod shadow,
    HxParameter parameter,
    HxInstruction instruction);

  static AspectStepParameterInjector with(final Collection<AspectStepParameterInjector> list) {
    return with(list.toArray(new AspectStepParameterInjector[0]));
  }

  static AspectStepParameterInjector with(final AspectStepParameterInjector ... list) {
    return (aspectStep, context, interceptor, method, shadow, parameter, instruction) -> {
      for(AspectStepParameterInjector injector : list) {
        if(injector.injectParameter(aspectStep, context, interceptor, method, shadow, parameter, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
