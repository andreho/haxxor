package net.andreho.aop.spec;

import net.andreho.aop.spec.query.Where;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 666 on 24.12.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotated {

  /**
   * Selects annotations that are named with specific fully-qualified names
   *
   * @return
   * @implNote defaults to empty {@link Named} annotation and so disables the selection by annotation's classname
   */
  Named named() default @Named();

  //AND

  /**
   * @return
   * @implNote defaults to {@link Annotated} and disables the direct selection annotation's class
   */
  Class<? extends Annotation> value() default Annotated.class;

  //AND

  /**
   * @return a list of conditions that are evaluated as disjunction, e.g.: <code>(A ∧ B) ∨ (B ∧ ¬C) ∨ ¬D</code>
   */
  Where[] criteria() default {};

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
