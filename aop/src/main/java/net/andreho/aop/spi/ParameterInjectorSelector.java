package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxMethod;

import java.util.List;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 23:37.
 */
public interface ParameterInjectorSelector {
  /**
   * @param injector
   * @return
   */
  static ParameterInjectorSelector create(final ParameterInjector ... injector) {
    return null;
  }

  /**
   * @param context
   * @param original
   * @param interceptor
   * @return
   */
  Optional<Result> selectSuitableInjectors(
    AspectContext context,
    HxMethod original,
    HxMethod interceptor
  );

  /**
   * @param context
   * @param original
   * @param interceptors
   * @return
   */
  default Optional<Result> selectBestSuitableInjectors(
    AspectContext context,
    HxMethod original,
    List<HxMethod> interceptors
  ) {
    Result current = null;
    for(HxMethod interceptor : interceptors) {
      Optional<Result> resultOptional = selectSuitableInjectors(context, original, interceptor);
      if(resultOptional.isPresent()) {
        Result result = resultOptional.get();
        if(current == null || current.score > result.score) {
          current = result;
        }
      }
    }
    return Optional.ofNullable(current);
  }

  class Result {
    public final int score;
    public final HxMethod method;
    public final List<ParameterInjector> injectors;

    public Result(final int score,
                  final HxMethod method,
                  final List<ParameterInjector> injectors) {
      this.score = score;
      this.method = method;
      this.injectors = injectors;
    }
  }
}
