package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 23:32.
 */
public @interface Position {

  /**
   * This attribute defines either a range with two values where: <code>a &lt; b</code>
   * and {@link #asRange()} being set to <b>true</b> or it defines an explicit selection of positions
   * @return either a range with two elements or a array with explicit position's listing
   */
  int[] value() default {};

  /**
   * @return
   */
  boolean asRange() default false;
}
