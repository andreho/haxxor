package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F1<R,A> extends FN<R>, Bindable<A,F0<R>> {
  @Override
  default F0<R> bind(final A target) {
    return () ->
      call(target);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object ... args) {
    return call((A) args[0]);
  }

  /**
   * @return
   */
  R call(A a);
}
