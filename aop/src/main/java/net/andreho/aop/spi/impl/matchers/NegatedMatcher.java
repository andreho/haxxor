package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 22:09.
 */
public final class NegatedMatcher<T> extends AbstractMatcher<T> {

  private final ElementMatcher<T> matcher;

  public NegatedMatcher(final ElementMatcher<T> matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean matches(final T t) {
    return !matcher.matches(t);
  }

  @Override
  public String toString() {
    return "NOT (" + matcher + ")";
  }
}
