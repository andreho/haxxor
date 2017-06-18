package net.andreho.aop.api.spec;

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
   * Selects parameters that are located at specific positions or position's range
   *
   * @return array with position-matcher
   * @implSpec defaults to an empty array and so disables the selection by parameter's position
   */
  Positioned[] positioned() default {}; //(... OR ...)
  //AND

  /**
   * Selects parameters having given modifiers.
   *
   * @return array with supported class modifiers
   * @implSpec defaults to an empty array and so disables the selection by modifiers
   */
  Modifier[] modifiers() default {}; //(... OR ...)
  //AND

  /**
   * Selection of parameters that have any one of listed types
   *
   * @return
   */
  Classes[] typed() default {}; //(... OR ...)
  //AND

  /**
   * Selection of parameters that are annotated with given annotations
   *
   * @return
   */
  Annotated[] annotated() default {}; //(... OR ...)
  //AND

  /**
   * Selects parameters that have parameters with given parameter's names
   *
   * @return array with name-matching selectors
   */
  Named[] named() default {}; //(... OR ...)

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;

}
