package net.andreho.aop.impl.search;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:58.
 */
public class ConjunctionFragment<T> extends AbstractFragment<T> {
  protected final AbstractFragment<T>[] array;

  public ConjunctionFragment(final Collection<AbstractFragment<T>> collection) {
    this(collection.toArray(new AbstractFragment[0]));
  }

  public ConjunctionFragment(final AbstractFragment<T>... array) {
    this.array = array;
  }

  @Override
  public boolean test(final T type) {
    for (AbstractFragment<T> fragment : array) {
      if (!check(fragment, type)) {
        return false;
      }
    }
    return true;
  }

  protected boolean check(final AbstractFragment<T> fragment,
                          final T element) {
    return fragment.test(element);
  }
}
