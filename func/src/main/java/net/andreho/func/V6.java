package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V6<A, B, C, D, E, F>
  extends VN,
          Bindable<A, V5<B, C, D, E, F>> {

  @Override
  default V5<B, C, D, E, F> bind(final A target) {
    return (b, c, d, e, f) ->
      call(target, (B) b, (C) c, (D) d, (E) e, (F) f);
  }

  default <R> F6<R,A,B,C,D,E,F> toFunc(final R result) {
    return (a, b, c, d, e, f) -> {
      call(a, b, c, d, e, f);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5]);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   * @param f
   */
  void call(A a,
            B b,
            C c,
            D d,
            E e,
            F f);
}
