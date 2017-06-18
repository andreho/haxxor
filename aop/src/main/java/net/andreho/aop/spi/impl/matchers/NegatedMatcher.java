package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 22:09.
 */
public final class NegatedMatcher<T> extends AbstractMatcher<T> {

  private final AspectMatcher<T> matcher;

  public NegatedMatcher(final AspectMatcher<T> matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean match(final T t) {
    return !matcher.match(t);
  }

  @Override
  public String toString() {
    return "NOT (" + matcher + ")";
  }
}
