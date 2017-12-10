package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V8<A, B, C, D, E, F, G, H>
  extends VN,
          Bindable<A, V7<B, C, D, E, F, G, H>> {

  @Override
  default V7<B, C, D, E, F, G, H> bind(final A target) {
    return (b, c, d, e, f, g, h) ->
      call(target, (B) b, (C) c, (D) d, (E) e, (F) f, (G) g, (H) h);
  }

  default <R> F8<R,A,B,C,D,E,F,G,H> toFunc(final R result) {
    return (a, b, c, d, e, f, g, h) -> {
      call(a, b, c, d, e, f, g, h);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5], (G) args[6], (H) args[7]);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   * @param f
   * @param g
   * @param h
   */
  void call(A a,
            B b,
            C c,
            D d,
            E e,
            F f,
            G g,
            H h);
}
