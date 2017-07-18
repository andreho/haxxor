package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F12<R,A,B,C,D,E,F,G,H,I,J,K,L> extends FN<R>, Bindable<A,F11<R,B,C,D,E,F,G,H,I,J,K,L>> {
  @Override
  default F11<R,B,C,D,E,F,G,H,I,J,K,L> bind(final A target) {
    return (b,c,d,e,f,g,h,i,j,k,l) ->
      call(target, (B) b, (C) c, (D) d, (E) e, (F) f, (G) g, (H) h, (I) i, (J) j, (K) k, (L) l);
  }

  /**
   * @param args
   * @return
   */
  default R call(Object... args) {
    return call((A) args[0],
                (B) args[1],
                (C) args[2],
                (D) args[3],
                (E) args[4],
                (F) args[5],
                (G) args[6],
                (H) args[7],
                (I) args[8],
                (J) args[9],
                (K) args[10],
                (L) args[11]);
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
   * @param i
   * @param j
   * @param k
   * @param l
   * @return
   */
  R call(A a,
         B b,
         C c,
         D d,
         E e,
         F f,
         G g,
         H h,
         I i,
         J j,
         K k,
         L l);
}
