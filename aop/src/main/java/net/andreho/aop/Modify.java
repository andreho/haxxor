package net.andreho.aop;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:23.
 */

import net.andreho.aop.spec.Classes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotations allows to modify the corresponding byte-code directly.
 * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Modify {
  /**
   * Explicit selection of classes that need to be processed
   * @return
   */
  Classes[] classes() default {};
}
