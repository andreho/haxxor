package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V5<A, B, C, D, E>
  extends VN,
          Bindable<A, V4<B, C, D, E>> {

  @Override
  default V4<B, C, D, E> bind(final A target) {
    return (b, c, d, e) ->
      call(target, (B) b, (C) c, (D) d, (E) e);
  }

  default <R> F5<R,A,B,C,D,E> toFunc(final R result) {
    return (a, b, c, d, e) -> {
      call(a, b, c, d, e);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4]);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   */
  void call(A a,
            B b,
            C c,
            D d,
            E e);
}
