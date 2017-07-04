package net.andreho.aop.spi;

import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface AspectAdvicePostProcessor {

  /**
   * @param aspectAdvice
   * @param context
   * @param interceptor
   * @param original
   * @param shadow
   *@param instruction  @return
   */
  boolean process(
    final AspectAdvice<?> aspectAdvice,
    final AspectContext context,
    final HxMethod interceptor,
    final HxMethod original,
    final HxMethod shadow,
    final HxInstruction instruction);

  static AspectAdvicePostProcessor with(final Collection<AspectAdvicePostProcessor> list) {
    return with(list.toArray(new AspectAdvicePostProcessor[0]));
  }

  static AspectAdvicePostProcessor with(final AspectAdvicePostProcessor... list) {
    return (aspectStep, context, interceptor, method, shadow, instruction) -> {
      for(AspectAdvicePostProcessor handler : list) {
        if(handler.process(aspectStep, context, interceptor, method, shadow, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
