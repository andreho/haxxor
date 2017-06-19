package net.andreho.aop.api.spec;

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
   * @return <b>true</b> if this selection must be treated as a list of ranges; <b>false</b> otherwise.
   */
  boolean asRange() default false;

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
