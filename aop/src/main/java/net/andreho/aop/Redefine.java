package net.andreho.aop;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to modify returned result-value of an intercepted method invocation.
 *
 * @implSpec the marked intercepting method should return either an expected type-compatible value or
 * can also return a not-null {@link net.andreho.aop.spi.redefine.Redefinition} instance.
 * @implNote this annotation can only be used in conjunction
 * with {@link Before}, {@link After} or {@link Catch} annotations
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Redefine {
   /**
    * @return injectable annotation types that should be redefined
    * @implNote supported are:
    * {@link net.andreho.aop.injectable.Caught},
    * {@link net.andreho.aop.injectable.Args},
    * {@link net.andreho.aop.injectable.Result}
    */
   Class<? extends Annotation>[] value() default {};
}
