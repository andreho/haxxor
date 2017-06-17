package net.andreho.aop.impl.search;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:22.
 */
public class DisjunctionFragment<T>
    extends AbstractFragment<T> {

  protected final AbstractFragment<T>[] array;

  public DisjunctionFragment(final Collection<AbstractFragment<T>> collection) {
    this(collection.toArray(new AbstractFragment[0]));
  }

  public DisjunctionFragment(final AbstractFragment<T>... array) {
    this.array = array;
  }

  @Override
  public boolean test(final T type) {
    if(array.length == 0) {
      return true;
    }

    for (AbstractFragment<T> fragment : array) {
      if (check(fragment, type)) {
        return true;
      }
    }
    return false;
  }

  protected boolean check(final AbstractFragment<T> fragment,
                          final T element) {
    return fragment.test(element);
  }
}
