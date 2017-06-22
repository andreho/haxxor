package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface AspectStepResultHandler {

  /**
   * @param aspectStep
   * @param context
   * @param interceptor
   * @param original
   * @param shadow
   *@param instruction  @return
   */
  boolean handleReturnValue(
    final AspectStep<?> aspectStep,
    final AspectContext context,
    final HxMethod interceptor,
    final HxMethod original,
    final HxMethod shadow,
    final HxInstruction instruction);

  static AspectStepResultHandler with(final Collection<AspectStepResultHandler> list) {
    return with(list.toArray(new AspectStepResultHandler[0]));
  }

  static AspectStepResultHandler with(final AspectStepResultHandler ... list) {
    return (aspectStep, context, interceptor, method, shadow, instruction) -> {
      for(AspectStepResultHandler handler : list) {
        if(handler.handleReturnValue(aspectStep, context, interceptor, method, shadow, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
