package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 23:32.
 */
public @interface Positioned {

  /**
   * This attribute defines either a range with <code>N * 2</code> values where: <code>N[i] &lt; N[i+1]</code>
   * and {@link #asRange()} being set to <b>true</b> or it defines an explicit selection of positions
   * @return either a range with <code>N * 2</code> elements or a array with explicit zero-based position's listing
   */
  int[] value() default {};

  /**
   * @return <b>true</b> if this selection has
   */
  boolean asRange() default false;
}
