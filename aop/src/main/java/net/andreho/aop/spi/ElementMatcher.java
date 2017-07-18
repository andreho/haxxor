package net.andreho.aop.spi;

import net.andreho.aop.spi.impl.matchers.AnyMatcher;
import net.andreho.aop.spi.impl.matchers.ConjunctionMatcher;
import net.andreho.aop.spi.impl.matchers.DisjunctionMatcher;
import net.andreho.aop.spi.impl.matchers.NegatedMatcher;
import net.andreho.aop.spi.impl.matchers.NoneMatcher;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
@FunctionalInterface
public interface ElementMatcher<T> {

  /**
   * @param element
   * @return
   */
  boolean matches(T element);

  /**
   * @return
   */
  default ElementMatcher<T> negate() {
    return new NegatedMatcher<>(this);
  }

  /**
   * @param condition to check; if positive then negate.
   * @return
   */
  default ElementMatcher<T> negateIf(final boolean condition) {
    return condition? new NegatedMatcher<>(this) : this;
  }

  /**
   * @param other
   * @return
   */
  default ElementMatcher<T> or(final ElementMatcher<T> other) {
    return or(this, other);
  }

  /**
   * @param other
   * @return
   */
  default ElementMatcher<T> and(final ElementMatcher<T> other) {
    return and(this, other);
  }

  /**
   * @return
   */
  default boolean isAny() {
    return this == any();
  }

  /**
   * @return
   */
  default boolean isNone() {
    return this == none();
  }

  /**
   * @return
   */
  default ElementMatcher<T> minimize() {
    return minimize(this);
  }

  /**
   * @return
   */
  default ElementMatcher<T> minimize(final ElementMatcher<T> alternative) {
    return isAny()? any() : alternative;
  }

  /**
   * @return
   */
  static <T> ElementMatcher<T> none() {
    return NoneMatcher.INSTANCE;
  }

  /**
   * @return
   */
  static <T> ElementMatcher<T> any() {
    return AnyMatcher.INSTANCE;
  }

  /**
   * @param collection
   * @return
   */
  static <T> ElementMatcher<T> or(final Collection<ElementMatcher<T>> collection) {
    return or(collection.toArray(new ElementMatcher[0]));
  }

  /**
   * @param array
   * @return
   */
  static <T> ElementMatcher<T> or(final ElementMatcher<T>... array) {
    if(array.length == 0) {
      return any();
    }
    return new DisjunctionMatcher<>(
      Stream.of(array).filter(m -> !m.isAny()).toArray(ElementMatcher[]::new)
    );
  }

  /**
   * @param collection
   * @return
   */
  static <T> ElementMatcher<T> and(final Collection<ElementMatcher<T>> collection) {
    return and(collection.toArray(new ElementMatcher[0]));
  }

  /**
   * @param array
   * @return
   */
  static <T> ElementMatcher<T> and(final ElementMatcher<T>... array) {
    if(array.length == 0) {
      return any();
    }
    return new ConjunctionMatcher<>(
      Stream.of(array).filter(m -> !m.isAny()).toArray(ElementMatcher[]::new)
    );
  }
}
