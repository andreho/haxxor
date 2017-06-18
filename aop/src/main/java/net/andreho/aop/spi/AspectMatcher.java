package net.andreho.aop.spi;

import net.andreho.aop.spi.impl.matchers.AnyMatcher;
import net.andreho.aop.spi.impl.matchers.ConjunctionMatcher;
import net.andreho.aop.spi.impl.matchers.DisjunctionMatcher;
import net.andreho.aop.spi.impl.matchers.NegatedMatcher;
import net.andreho.aop.spi.impl.matchers.NoneMatcher;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
@FunctionalInterface
public interface AspectMatcher<T> {

  /**
   * @param element
   * @return
   */
  boolean match(T element);

  /**
   * @return
   */
  default AspectMatcher<T> negate() {
    return new NegatedMatcher<>(this);
  }

  /**
   * @param condition to check; if positive then negate.
   * @return
   */
  default AspectMatcher<T> negateIf(boolean condition) {
    return condition? new NegatedMatcher<>(this) : this;
  }

  /**
   * @param other
   * @return
   */
  default AspectMatcher<T> or(AspectMatcher<T> other) {
    return or(this, other);
  }

  /**
   * @param other
   * @return
   */
  default AspectMatcher<T> and(AspectMatcher<T> other) {
    return and(this, other);
  }

  /**
   * @return
   */
  default boolean isAny() {
    return this == AnyMatcher.INSTANCE;
  }

  /**
   * @return
   */
  default boolean isNone() {
    return this == NoneMatcher.INSTANCE;
  }

  /**
   * @return
   */
  static <T> AspectMatcher<T> none() {
    return NoneMatcher.INSTANCE;
  }

  /**
   * @return
   */
  static <T> AspectMatcher<T> any() {
    return AnyMatcher.INSTANCE;
  }

  /**
   * @param collection
   * @return
   */
  static <T> AspectMatcher<T> or(Collection<AspectMatcher<T>> collection) {
    return or(collection.toArray(new AspectMatcher[0]));
  }

  /**
   * @param array
   * @return
   */
  static <T> AspectMatcher<T> or(final AspectMatcher<T>... array) {
    if(array.length == 0) {
      return any();
    }
    return new DisjunctionMatcher<>(array);
  }

  /**
   * @param collection
   * @return
   */
  static <T> AspectMatcher<T> and(Collection<AspectMatcher<T>> collection) {
    return and(collection.toArray(new AspectMatcher[0]));
  }

  /**
   * @param array
   * @return
   */
  static <T> AspectMatcher<T> and(final AspectMatcher<T>... array) {
    if(array.length == 0) {
      return any();
    }
    return new ConjunctionMatcher<>(array);
  }
}
