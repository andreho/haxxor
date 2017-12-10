package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V3<A,B,C> extends VN, Bindable<A,V2<B,C>> {
  @Override
  default V2<B,C> bind(final A target) {
    return (b,c) ->
      call(target, (B) b, (C) c);
  }

  default <R> F3<R,A,B,C> toFunc(final R result) {
    return (a, b, c) -> {
      call(a, b, c);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1], (C) args[2]);
  }

  /**
   * @param a
   * @param b
   */
  void call(A a,
         B b,
         C c);
}
