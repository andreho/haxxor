package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxOwned;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:34.
 */
public class DeclaredByMatcher<O extends HxOwned<O>>
  extends AbstractMatcher<O> {
  private final ElementMatcher<HxType> delegate;

  public DeclaredByMatcher(final ElementMatcher<HxType> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean isAny() {
    return delegate.isAny();
  }

  @Override
  public boolean match(final O element) {
    return delegate.match((HxType) element.getDeclaringMember());
  }

  @Override
  public String toString() {
    return "DECLARED_BY "+delegate.toString();
  }
}