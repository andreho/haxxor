package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public @interface SignatureRef {
//   /**
//    * @return
//    */
//   int minArity() default 0;
//
//   /**
//    * @return
//    */
//   int maxArity() default Short.MAX_VALUE;

   /**
    * Selection of signatures that are equal to given class list.<br/>
    * Defaults to empty parameter list
    *
    * @return
    */
   Class<?>[] value() default {};
}
