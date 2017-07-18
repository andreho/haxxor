package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F2<R,A,B> extends FN<R>, Bindable<A,F1<R,B>> {
  @Override
  default F1<R, B> bind(final A target) {
    return (b) ->
      call(target, (B) b);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0], (B) args[1]);
  }

  /**
   * @param a
   * @param b
   * @return
   */
  R call(A a, B b);
}
