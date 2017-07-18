package net.andreho.func;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 19:47.
 */
@FunctionalInterface
public interface F17<R,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q>
  extends FN<R>, Bindable<A,F16<R,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q>> {

  @Override
  default F16<R,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q> bind(final A target) {
    return (b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q) ->
      call(target,
           (B) b, (C) c, (D) d, (E) e, (F) f, (G) g,
           (H) h, (I) i, (J) j, (K) k, (L) l, (M) m, (N) n, (O) o, (P) p, (Q) q);
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
                (L) args[11],
                (M) args[12],
                (N) args[13],
                (O) args[14],
                (P) args[15],
                (Q) args[16]);
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
   * @param m
   * @param n
   * @param o
   * @param p
   * @param q
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
         L l,
         M m,
         N n,
         O o,
         P p,
         Q q);
}
