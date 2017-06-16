package net.andreho.aop;

import net.andreho.aop.transform.Transformer;

/**
 * Allows to define the order of execution of some elements
 * <br/>Created by a.hofmann on 29.05.2017 at 22:00.
 * @see Aspect
 * @see Transformer
 */
public @interface Order {
  /**
   * The order index of the marked element. A <b>smaller value</b> leads to greater ordering priority.
   * @return the order index
   * @implSpec default value is the lowest possible value,
   * so any other value would lead to greater priority
   */
  int value() default Integer.MAX_VALUE;
}
