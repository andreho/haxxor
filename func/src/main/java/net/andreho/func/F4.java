package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F4<R,A,B,C,D> extends FN<R>, Bindable<A,F3<R,B,C,D>> {
  @Override
  default F3<R,B,C,D> bind(final A target) {
    return (b,c,d) ->
      call(target, (B) b, (C) c, (D) d);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0], (B) args[1], (C) args[2], (D) args[3]);
  }

  /**
   * @param a
   * @param b
   * @return
   */
  R call(A a, B b, C c, D d);
}
