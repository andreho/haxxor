package net.andreho.aop.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to define the order of execution of some elements
 * <br/>Created by a.hofmann on 29.05.2017 at 22:00.
 * @see Aspect
 * @see Modify.Type
 * @see Modify.Field
 * @see Modify.Method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Order {
  /**
   * The order index of the marked element. A <b>smaller value</b> leads to greater ordering priority.
   * @return the order index
   * @implSpec default value is the lowest possible value,
   * so any other value would lead to greater priority
   */
  int value();
}
