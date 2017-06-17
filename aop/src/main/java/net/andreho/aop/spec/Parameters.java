package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameters {
  /**
   * Selects parameters that are located at some specific positions
   *
   * @return array with position-matching
   * @implSpec defaults to an empty array and so disables the selection by parameter's position
   */
  Positioned[] positioned() default {};
  //AND

  /**
   * Selects classes having given modifiers.
   *
   * @return array with supported class modifiers
   * @implSpec defaults to an empty array and so disables the selection by modifiers
   */
  Modifier[] modifiers() default {};
  //AND

  /**
   * Selection of parameters that have any one of listed types
   *
   * @return
   */
  Classes[] typed() default {};
  //AND

  /**
   * Selection of parameters that are annotated with given annotations
   *
   * @return
   */
  Annotated[] annotated() default {};
  //AND

  /**
   * Selects parameters that have parameters with given parameter's names
   *
   * @return array with name-matching selectors
   */
  Named[] named() default {};

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;

}
