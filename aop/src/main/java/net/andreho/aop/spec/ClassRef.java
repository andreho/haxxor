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
public @interface ClassRef {
   /**
    * @return
    */
   ClassSelector[] value() default {};

   /**
    * Selects classes that extend given classes.
    * Empty array to disable filtering by class hierarchy.
    *
    * @return array with parent classes
    */
   ClassSelector[] extending() default {};

   /**
    * Selects classes that implement given interfaces. It only handles known interface types.
    * Empty array to disable filtering by class hierarchy.
    *
    * @return array with interfaces
    */
   ClassSelector[] implementing() default {};

   /**
    * Selects classes that are annotated with the given annotations.
    * It only handles known annotation types.
    * Empty array to disable filtering by annotations.
    *
    * @return array with annotations
    */
   Annotated[] annotated() default {};
}
