package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Marker;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to install additional <code>catch</code> blocks into intercepted method
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Supports({
   Arg.class,
   Args.class,
   Arity.class,
   Declaring.class,
   Intercepted.class,
   Marker.class,
   Line.class,
   Caught.class,
   This.class})
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Catch {
   /**
    * @return {@link Throwable} types of expected exceptions, that need to be handled by this aspect
    */
   Class<? extends Throwable>[] value() default {Throwable.class};

   /**
    * Which methods should be processed
    *
    * @return a set of method-selectors that select matched methods
    */
   Methods[] methods() default {};
}
