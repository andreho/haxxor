package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 23:32.
 */
public @interface Position {
   /**
    * @return
    */
   int[] value() default {};

   /**
    * @return
    */
   boolean asRange() default false;
}
