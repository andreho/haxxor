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
    * @return
    */
   Named[] name() default @Named();

   /**
    * @return
    */
   Class<? extends Annotation> value() default Annotation.class;

   /**
    * @return a list of conditions that are evaluated as disjunction, e.g.: <code>(A ∧ B) ∨ (B ∧ ¬C) ∨ ¬D</code>
    */
   Where[] criteria() default {};
}
