package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V1<A> extends VN, Bindable<A,V0> {
  @Override
  default V0 bind(final A target) {
    return () ->
      call(target);
  }

  default <R> F1<R,A> toFunc(final R result) {
    return (a) -> {
      call(a);
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call((A) args[0]);
  }

  /**
   */
  void call(A a);
}
