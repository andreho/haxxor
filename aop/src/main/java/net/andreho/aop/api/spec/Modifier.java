package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 23:27.
 * @see Modifiers
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Modifier {

  /**
   * Selection of fields with given modifiers.
   *
   * @return an array with specific modifiers
   * @implNote by default matches any modifiers
   * @see Modifiers
   */
  int value() default Modifiers.ANY;

  /**
   * Allows to negate the result of modifiers' matching
   *
   * @return <b>true</b> to negate selection result, <b>false</b> to leave as it is
   */
  boolean negate() default false;
}
