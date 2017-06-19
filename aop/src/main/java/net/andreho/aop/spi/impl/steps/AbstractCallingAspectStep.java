package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxMethod;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public abstract class AbstractCallingAspectStep<T> extends AbstractAspectStep<T> {
  private final HxMethod interceptor;

  public AbstractCallingAspectStep(final int index,
                                   final AspectStepType type,
                                   final ElementMatcher<T> elementMatcher,
                                   final String profileName,
                                   final HxMethod interceptor) {
    super(index,
          type,
          elementMatcher,
          asOptional(profileName));
    this.interceptor = interceptor;
  }

  private static Optional<String> asOptional(final String profileName) {
    return profileName == null || profileName.isEmpty() ? Optional.empty() : Optional.of(profileName);
  }

  @Override
  public HxMethod getInterceptor() {
    return interceptor;
  }
}
