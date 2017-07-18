package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F3<R,A,B,C> extends FN<R>, Bindable<A,F2<R,B,C>> {
  @Override
  default F2<R,B,C> bind(final A target) {
    return (b,c) ->
      call(target, (B) b, (C) c);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0], (B) args[1], (C) args[2]);
  }

  /**
   * @param a
   * @param b
   * @return
   */
  R call(A a, B b, C c);
}
