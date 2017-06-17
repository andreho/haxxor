package net.andreho.aop.impl.search;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 22:09.
 */
public final class NegatedFragment<T> extends AbstractFragment<T> {

  private final AbstractFragment<T> fragment;

  public NegatedFragment(final AbstractFragment<T> fragment) {
    this.fragment = fragment;
  }

  @Override
  public boolean test(final T t) {
    return !fragment.test(t);
  }

  @Override
  public String toString() {
    return "NOT "+fragment;
  }
}
