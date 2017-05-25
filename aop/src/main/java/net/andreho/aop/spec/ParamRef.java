package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public @interface ParamRef {
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
