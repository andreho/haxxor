package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F5<R,A,B,C,D,E> extends FN<R>, Bindable<A,F4<R,B,C,D,E>> {
  @Override
  default F4<R,B,C,D,E> bind(final A target) {
    return (b,c,d,e) ->
      call(target, (B) b, (C) c, (D) d, (E) e);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4]);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   * @return
   */
  R call(A a, B b, C c, D d, E e);
}
