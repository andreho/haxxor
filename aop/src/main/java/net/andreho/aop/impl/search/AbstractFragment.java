package net.andreho.aop.impl.search;

import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 21:43.
 */
public abstract class AbstractFragment<T> implements Predicate<T> {

  @Override
  public AbstractFragment<T> negate() {
    return new NegatedFragment<>(this);
  }
}
