package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V7<A, B, C, D, E, F, G>
  extends VN,
          Bindable<A, V6<B, C, D, E, F, G>> {

  @Override
  default V6<B, C, D, E, F, G> bind(final A target) {
    return (b, c, d, e, f, g) ->
      call(target, (B) b, (C) c, (D) d, (E) e, (F) f, (G) g);
  }

  default <R> F7<R,A,B,C,D,E,F,G> toFunc(final R result) {
    return (a, b, c, d, e, f, g) -> {
      call(a, b, c, d, e, f, g);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5], (G) args[6]);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   * @param f
   * @param g
   */
  void call(A a,
            B b,
            C c,
            D d,
            E e,
            F f,
            G g);
}
