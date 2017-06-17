package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to reference classes having different properties
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Classes {

  /**
   * Selects the main set of classes that are merged with other conditions
   * (having annotations, extending/implementing interfaces, etc.)
   *
   * @return
   */
  ClassWith[] value() default {};

  //AND

  /**
   * Selects classes that extend given classes.
   *
   * @return array with parent classes
   * @implNote empty array to disable filtering by class hierarchy.
   */
  ClassWith[] extending() default {};

  //AND

  /**
   * Selects classes that implement given interfaces. It only handles available for current runtime interfaces.
   *
   * @return array with interface selectors
   * @implNote empty array to disable filtering by class hierarchy.
   */
  ClassWith[] implementing() default {};
}
