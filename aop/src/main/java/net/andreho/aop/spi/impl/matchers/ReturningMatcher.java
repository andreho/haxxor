package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:34.
 */
public class ReturningMatcher
    extends AbstractMatcher<HxMethod> {
  private final ElementMatcher<HxType> delegate;

  public ReturningMatcher(final ElementMatcher<HxType> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean isAny() {
    return delegate.isAny();
  }

  @Override
  public boolean matches(final HxMethod method) {
    return delegate.matches(method.getReturnType());
  }

  @Override
  public String toString() {
    return "RETURNING " + delegate.toString();
  }
}
