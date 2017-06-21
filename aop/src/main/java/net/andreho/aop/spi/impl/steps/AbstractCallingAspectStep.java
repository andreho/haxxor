package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepResultHandler;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxMethod;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractCallingAspectStep<T> extends AbstractAspectStep<T> {
  private final HxMethod interceptor;
  private final AspectStepParameterInjector parameterInjector;
  private final AspectStepResultHandler resultHandler;

  public AbstractCallingAspectStep(final int index,
                                   final AspectStepType type,
                                   final ElementMatcher<T> elementMatcher,
                                   final String profileName,
                                   final HxMethod interceptor,
                                   final AspectStepParameterInjector parameterInjector,
                                   final AspectStepResultHandler resultHandler) {
    super(index,
          type,
          elementMatcher,
          asOptional(profileName));

    this.interceptor = requireNonNull(interceptor);
    this.parameterInjector = requireNonNull(parameterInjector);
    this.resultHandler = requireNonNull(resultHandler);
  }

  private static Optional<String> asOptional(final String profileName) {
    return profileName == null || profileName.isEmpty() ? Optional.empty() : Optional.of(profileName);
  }

  @Override
  public boolean needsAspectFactory() {
    return !getInterceptor().isStatic();
  }

  @Override
  public HxMethod getInterceptor() {
    return interceptor;
  }

  public AspectStepParameterInjector getParameterInjector() {
    return parameterInjector;
  }

  public AspectStepResultHandler getResultHandler() {
    return resultHandler;
  }
}
