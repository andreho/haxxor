package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
@FunctionalInterface
public interface ResultPostProcessor {

  /**
   * @param aspectAdvice
   * @param context
   * @param interceptor
   * @param original
   * @param shadow
   * @param anchor
   * @return
   */
  boolean handle(
    final AspectAdvice<?> aspectAdvice,
    final AspectContext context,
    final HxMethod interceptor,
    final HxMethod original,
    final HxMethod shadow,
    final HxInstruction anchor);

  static ResultPostProcessor list(final Collection<ResultPostProcessor> list) {
    return list(list.toArray(new ResultPostProcessor[0]));
  }

  static ResultPostProcessor list(final ResultPostProcessor... list) {
    return (aspectStep, context, interceptor, method, shadow, instruction) -> {
      for(ResultPostProcessor postProcessor : list) {
        if(postProcessor.handle(aspectStep, context, interceptor, method, shadow, instruction)) {
          return true;
        }
      }
      return false;
    };
  }
}
