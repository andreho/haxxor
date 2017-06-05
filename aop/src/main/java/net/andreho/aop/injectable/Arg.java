package net.andreho.aop.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects the concrete argument that was passed to intercepted method call.<br/>
 * The selection is made either by desired argument's index or by argument's name or by it's type.
 * For methods without parameters a default value (accordingly to the argument's type:
 * <code>boolean = false</code>, <code>int = 0</code>, <code>Object =
 * null</code>, etc.) is going to be passed as intercepted value.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {
   /**
    * @return the allowed indices of the intercepted argument
    */
   int[] index() default -1;

   /**
    * @return the allowed names of the intercepted argument
    */
   String[] name() default {};

   /**
    * @return preferred argument's types
    */
   Class<?>[] value() default {};
}