package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 05.10.2017 at 13:14.
 */
@FunctionalInterface
public interface ParameterInjector {
  int SCALE_STEP = 1;
  int MAX_SUITABLE = SCALE_STEP * 1_000_000;
  int MIN_SUITABLE = 0;
  int NOT_INJECTABLE = -1;

  /**
   * @param context
   * @param original
   * @param interceptor
   * @param parameter
   * @return
   */
  default boolean canInject(AspectContext context,
                            HxMethod original,
                            HxMethod interceptor,
                            HxParameter parameter) {
    return score(context, original, interceptor, parameter) > NOT_INJECTABLE;
  }

  /**
   * Calculates abstract score for injection with this injector and given parameters
   *
   * @param context     of current modification
   * @param original    that should be modified
   * @param interceptor to invoke
   * @param parameter   that must be populated with a suitable value
   * @return <b>negative value</b> if this injector isn't suitable otherwise a positive value
   */
  default int score(
    AspectContext context,
    HxMethod original,
    HxMethod interceptor,
    HxParameter parameter) {

    return NOT_INJECTABLE;
  }

  /**
   * Allocates value for the given interceptor's parameter
   *
   * @param aspectAdvice is the currently processed step
   * @param context      of current modification
   * @param original     that should be modified
   * @param shadow       that should be executed
   * @param interceptor  to invoke and whose parameters to populate
   * @param parameter    that must be populated with a suitable value
   * @param anchor       in bytecode for injection instructions
   */
  void inject(
    AspectAdvice<?> aspectAdvice,
    AspectContext context,
    HxMethod original,
    HxMethod shadow,
    HxMethod interceptor,
    HxParameter parameter,
    HxInstruction anchor);
}
