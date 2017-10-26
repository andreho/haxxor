package net.andreho.aop.spi.impl.injectors;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ParameterInjector;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.andreho.aop.spi.ParameterInjector.NOT_INJECTABLE;

/**
 * <br/>Created by a.hofmann on 08.10.2017 at 13:24.
 */
public class ParameterInjectorSelectorImpl
  implements ParameterInjectorSelector {
  private final ParameterInjector[] injectors;

  public ParameterInjectorSelectorImpl(final ParameterInjector... injectors) {
    this.injectors = Objects.requireNonNull(injectors).clone();
  }

  public ParameterInjector[] getInjectors() {
    return injectors.clone();
  }

  @Override
  public Optional<Result> selectSuitableInjectors(final AspectContext context,
                                                  final HxMethod original,
                                                  final HxMethod interceptor) {
    List<ParameterInjector> currentInjectors = new ArrayList<>(interceptor.getParametersCount());
    int currentScore = 0;

    for(final HxParameter parameter : interceptor.getParameters()) {
      ParameterInjector bestInjector = null;
      int bestScore = NOT_INJECTABLE;

      for(final ParameterInjector injector : this.injectors) {
        int reachedScore = injector.score(context, original, interceptor, parameter);

        if(reachedScore > NOT_INJECTABLE && reachedScore > bestScore) {
          bestScore = reachedScore;
          bestInjector = injector;
        }
      }

      if(bestScore > NOT_INJECTABLE && bestInjector != null) {
        currentScore += bestScore;
        currentInjectors.add(bestInjector);
      } else {
        return Optional.empty();
      }
    }

    return Optional.of(new Result(currentScore, interceptor, currentInjectors));
  }
}
