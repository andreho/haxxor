package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.HxTyped;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:34.
 */
public class TypedMatcher<T extends HxTyped>
    extends AbstractMatcher<T> {
  private final ElementMatcher<HxType> delegate;

  public TypedMatcher(final ElementMatcher<HxType> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean isAny() {
    return delegate.isAny();
  }

  @Override
  public boolean matches(final T typed) {
    return delegate.matches(typed.getType());
  }

  @Override
  public String toString() {
    return "TYPED " + delegate.toString();
  }
}
