package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by a.hofmann on 20.05.2016.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassSelector {
   /**
    * Selects classes having given modifiers.
    *
    * @return array with supported class modifiers
    * @implSpec defaults to an empty array and so disables the selection by modifiers
    */
   Modifier[] modifiers() default {};

   /**
    * Selects given classes explicitly.
    *
    * @return array with explicit classes
    * @implSpec defaults to an empty array and so disables the explicit class selection
    */
   Class<?>[] classes() default {};

   /**
    * Selects classes by their fully qualified classnames
    *
    * @return a list with name selectors to filter classes by their fully qualified type names
    * @implNote a fully qualified binary classname can be for example: <code>java.lang.String</code> or <code>int[]</code>
    * @implSpec defaults to an empty array and so disables the selection by classname
    * @see Named
    */
   Named[] value() default {};
}
