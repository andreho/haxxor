package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamRef {

   /**
    * @return
    */
   Position[] positioned() default {};

   /**
    * @return
    */
   Named[] named() default {};

   /**
    * Selection of parameters that have any one of listed types
    *
    * @return
    */
   ClassRef[] typed() default {};

   /**
    * Selection of parameters that are annotated with given annotations
    *
    * @return
    */
   Annotated[] annotated() default {};
}
