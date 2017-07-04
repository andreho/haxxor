package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F0<R> extends FX<R>, Bindable<Object,F0<R>> {
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
