package net.andreho.aop.spi.impl.matchers;

import net.andreho.aop.spi.ElementMatcher;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 02:58.
 */
public class ConjunctionMatcher<T>
    extends AbstractMatcher<T> {

  protected final ElementMatcher<T>[] array;

  public ConjunctionMatcher(final Collection<ElementMatcher<T>> collection) {
    this(collection.toArray(new ElementMatcher[0]));
  }

  public ConjunctionMatcher(final ElementMatcher<T>... array) {
    this.array = array;
  }

  @Override
  public boolean matches(final T type) {
    for (ElementMatcher<T> matcher : array) {
      if (!check(matcher, type)) {
        return false;
      }
    }
    return true;
  }

  protected boolean check(final ElementMatcher<T> fragment,
                          final T element) {
    return fragment.matches(element);
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
