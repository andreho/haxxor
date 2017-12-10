package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface V0
  extends VN, Bindable<Object,V0> {

  @Override
  default V0 bind(final Object target) {
    return this;
  }

  default <R> F0<R> toFunc(final R result) {
    return () -> {
      call();
      return result;
    };
  }

  /**
   * @param args
   */
  default void call(Object... args) {
    call();
  }

  /**
   */
  void call();
}
