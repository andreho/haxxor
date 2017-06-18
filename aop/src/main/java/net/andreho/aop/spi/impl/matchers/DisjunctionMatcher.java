package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 01:22.
 */
public class DisjunctionMatcher<T>
    extends AbstractMatcher<T> {

  protected final AspectMatcher<T>[] array;

  public DisjunctionMatcher(final Collection<AspectMatcher<T>> collection) {
    this(collection.toArray(new AspectMatcher[0]));
  }

  public DisjunctionMatcher(final AspectMatcher<T>... array) {
    this.array = array;
  }

  @Override
  public boolean isAny() {
    return array.length == 0;
  }

  @Override
  public boolean match(final T type) {
    if(isAny()) {
      return true;
    }

    for (AspectMatcher<T> matcher : array) {
      if (check(matcher, type)) {
        return true;
      }
    }
    return false;
  }

  protected boolean check(final AspectMatcher<T> fragment,
                          final T element) {
    return fragment.match(element);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("[");

    if(array.length > 0) {
      builder.append(array[0]);
      for (int i = 1; i < array.length; i++) {
        builder.append(" OR ").append(array[0]);
      }
    } else {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
