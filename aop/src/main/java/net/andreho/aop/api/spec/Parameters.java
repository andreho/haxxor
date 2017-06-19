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
   * @return array with position-matching selectors
   * @apiNote defaults to an empty array and so disables the selection by parameter's position;
   * elements are bound via an OR (disjunction)
   */
  Positioned[] positioned() default {}; //(... OR ...)
  //AND

  /**
   * Selection of parameters that have any one of listed types
   *
   * @return array with type-matching selectors
   * @apiNote defaults to an empty array and so disables the selection by parameter's types;
   * elements are bound via an OR (disjunction)
   */
  Classes[] typed() default {}; //(... OR ...)
  //AND

  /**
   * Selection of parameters that are annotated with given annotations
   *
   * @return array with annotation-matching selectors
   * @apiNote defaults to an empty array and so disables the selection by parameter's annotation;
   * elements are bound via an OR (disjunction)
   */
  Annotated[] annotated() default {}; //(... OR ...)
  //AND

  /**
   * Selects parameters that have parameters with given parameter's names
   *
   * @return array with name-matching selectors
   * @apiNote defaults to an empty array and so disables the selection by parameter's name;
   * elements are bound via an OR (disjunction)
   */
  Named[] named() default {}; //(... OR ...)

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;

}
