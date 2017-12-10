package net.andreho.func;

import java.util.concurrent.Callable;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F0<R> extends FN<R>,
                               Bindable<Object,F0<R>>,
                               Callable<R> {
  @Override
  default F0<R> bind(final Object target) {
    return this;
  }

  /**
   * @param args
   * @return
   */
  default R call(Object ... args) {
    return call();
  }

  /**
   * @return
   */
  R call();
}
