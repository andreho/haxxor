package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:34.
 */
public class ReturningMatcher<E extends HxExecutable<E>>
    extends AbstractMatcher<E> {
  private final AspectMatcher<HxType> delegate;

  public ReturningMatcher(final AspectMatcher<HxType> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean isAny() {
    return delegate.isAny();
  }

  @Override
  public boolean match(final E method) {
    if(method instanceof HxMethod) {
      return delegate.match(((HxMethod) method).getReturnType());
    }
    return false;
  }

  @Override
  public String toString() {
    return "RETURNING " + delegate.toString();
  }
}
