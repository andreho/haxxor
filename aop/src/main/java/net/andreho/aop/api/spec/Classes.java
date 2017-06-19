package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to reference classes having different properties
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Classes {

  /**
   * Selects the main set of classes that are merged with other conditions
   * (having types, annotations, modifiers, names, etc.)
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  ClassWith[] value() default {}; //(... OR ...)

  //AND

  /**
   * Selects classes that extend given classes.
   *
   * @return array with parent classes
   * @apiNote empty array to disable filtering by class hierarchy;
   * elements are bound via an OR (disjunction)
   * @implNote this operation may be expensive
   */
  ClassWith[] extending() default {}; //(... OR ...)

  //AND

  /**
   * Selects classes that implement given interfaces. It only handles available for current runtime interfaces.
   *
   * @return array with interface selectors
   * @apiNote empty array to disable filtering by class hierarchy;
   * elements are bound via an OR (disjunction)
   * @implNote this operation may be expensive
   */
  ClassWith[] implementing() default {}; //(... OR ...)
}
