package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by a.hofmann on 20.05.2016.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassWith {

  /**
   * Selects classes having given modifiers.
   *
   * @return array with supported class modifiers
   * @apiNote defaults to an empty array and so disables the selection by modifiers;
   * elements are bound via an OR (disjunction)
   */
  Modifier[] modifiers() default {}; //(... OR ...)

  //AND

  /**
   * Selects classes by their fully qualified classnames
   *
   * @return a list with name selectors to filter classes by their fully qualified type names
   * @implNote a fully qualified binary classname can be for example: <code>double</code>, <code>java.lang.String</code> or
   * <code>int[]</code> etc.
   * @apiNote defaults to an empty array and so disables the selection by classname;
   * elements are bound via an OR (disjunction)
   * @see Named
   */
  Named[] named() default {}; //(... OR ...)

  //AND

  /**
   * Selects given classes explicitly.
   *
   * @return array with explicit classes
   * @apiNote defaults to an empty array and so disables the explicit class selection;
   * elements are bound via an OR (disjunction)
   */
  Class<?>[] value() default {}; //(... OR ...)

  //AND

  /**
   * Selects classes that are annotated with the given annotations.
   * It only handles known annotation types.
   * Empty array to disable filtering by annotations.
   * @return array with annotations
   * @apiNote defaults to an empty array and so disables the selection by annotation;
   * elements are bound via an OR (disjunction)
   */
  Annotated[] annotated() default {}; //(... OR ...)

  /**
   * Allows to inverse this selection
   *
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
