package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Result;

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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Redefine {
   /**
    * @return injectable annotation types that should be redefined
    * @apiNote supported are:
    * {@link Caught},
    * {@link Args},
    * {@link Result}
    */
   Class<? extends Annotation>[] value() default {};
}
