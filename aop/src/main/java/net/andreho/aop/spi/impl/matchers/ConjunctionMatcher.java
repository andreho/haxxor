package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.AspectMatcher;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:58.
 */
public class ConjunctionMatcher<T>
    extends AbstractMatcher<T> {

  protected final AspectMatcher<T>[] array;

  public ConjunctionMatcher(final Collection<AspectMatcher<T>> collection) {
    this(collection.toArray(new AspectMatcher[0]));
  }

  public ConjunctionMatcher(final AspectMatcher<T>... array) {
    this.array = array;
  }

  @Override
  public boolean match(final T type) {
    for (AspectMatcher<T> matcher : array) {
      if (!check(matcher, type)) {
        return false;
      }
    }
    return true;
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
        builder.append(" AND ").append(array[0]);
      }
    } else {
      builder.append("ANY");
    }

    return builder.append("]").toString();
  }
}
