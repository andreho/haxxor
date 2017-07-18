package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F8<R,A,B,C,D,E,F,G,H> extends FN<R>, Bindable<A,F7<R,B,C,D,E,F,G,H>> {
  @Override
  default F7<R,B,C,D,E,F,G,H> bind(final A target) {
    return (b,c,d,e,f,g,h) ->
      call(target, (B) b, (C) c, (D) d, (E) e, (F) f, (G) g, (H) h);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0], (B) args[1], (C) args[2], (D) args[3], (E) args[4], (F) args[5], (G) args[6], (H) args[7]);
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
   * @return
   */
  R call(A a, B b, C c, D d, E e, F f, G g, H h);
}
