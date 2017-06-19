package net.andreho.aop.api.spec;

import net.andreho.aop.api.spec.query.Where;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 666 on 24.12.2016.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotated {

  /**
   * Selects an annotation directly by its class
   * @return a type of the annotation
   * @apiNote defaults to {@link Annotated} and disables the selection by annotation's class
   */
  Class<? extends Annotation> value() default Annotated.class;
  //AND

  /**
   * Selects annotations that are named with specific fully-qualified names
   * @return
   * @apiNote defaults to empty {@link Named} annotation and so disables the selection by annotation's classname
   */
  Named named() default @Named();
  //AND

  /**
   * @return a list of conditions that are evaluated as disjunction, e.g.: <code>(A ∧ B) ∨ (B ∧ ¬C) ∨ ¬D</code>
   */
  Where[] criteria() default {}; //(... OR ...)

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
