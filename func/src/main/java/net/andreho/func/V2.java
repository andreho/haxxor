package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V2<A,B> extends VN, Bindable<A, V1<B>> {
  @Override
  default V1<B> bind(final A target) {
    return (b) ->
      call(target, (B) b);
  }

  default <R> F2<R,A,B> toFunc(final R result) {
    return (a, b) -> {
      call(a, b);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0], (B) args[1]);
  }

  /**
   * @param a
   * @param b
   */
  void call(A a,
         B b);
}
