package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Signatures {

  /**
   * Selection of signatures that are equal to given class list.<br/>
   *
   * @return
   * @implNote defaults to an array with a {@link Void#TYPE void} and so disables this matching
   */
  String[] classes() default "void";
  //OR

  /**
   * Selection of signatures that are equal to given class list.<br/>
   *
   * @return
   * @implNote defaults to an array with a {@link Void#TYPE void} and so disables this matching
   */
  Class<?>[] value() default void.class;

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
