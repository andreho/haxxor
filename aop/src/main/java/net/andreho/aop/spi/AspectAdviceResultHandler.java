package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface AspectAdviceResultHandler {

  /**
   * @param aspectAdvice
   * @param context
   * @param interceptor
   * @param original
   * @param shadow
   *@param instruction  @return
   */
  boolean handleReturnValue(
    final AspectAdvice<?> aspectAdvice,
    final AspectContext context,
    final HxMethod interceptor,
    final HxMethod original,
    final HxMethod shadow,
    final HxInstruction instruction);

  static AspectAdviceResultHandler with(final Collection<AspectAdviceResultHandler> list) {
    return with(list.toArray(new AspectAdviceResultHandler[0]));
  }

  static AspectAdviceResultHandler with(final AspectAdviceResultHandler... list) {
    return (aspectStep, context, interceptor, method, shadow, instruction) -> {
      for(AspectAdviceResultHandler handler : list) {
        if(handler.handleReturnValue(aspectStep, context, interceptor, method, shadow, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
