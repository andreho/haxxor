package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V4<A, B, C, D>
  extends VN,
          Bindable<A, V3<B, C, D>> {

  @Override
  default V3<B, C, D> bind(final A target) {
    return (b, c, d) ->
      call(target, (B) b, (C) c, (D) d);
  }

  default <R> F4<R,A,B,C,D> toFunc(final R result) {
    return (a, b, c, d) -> {
      call(a, b, c, d);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2], (D) args[3]);
  }

  /**
   * @param a
   * @param b
   */
  void call(A a,
            B b,
            C c,
            D d);
}
