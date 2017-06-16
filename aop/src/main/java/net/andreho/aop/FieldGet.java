package net.andreho.aop;

import net.andreho.aop.injectable.Arg;
import net.andreho.aop.injectable.Args;
import net.andreho.aop.injectable.Arity;
import net.andreho.aop.injectable.Current;
import net.andreho.aop.injectable.Declaring;
import net.andreho.aop.injectable.Intercepted;
import net.andreho.aop.injectable.Line;
import net.andreho.aop.injectable.Marker;
import net.andreho.aop.injectable.Result;
import net.andreho.aop.injectable.This;
import net.andreho.aop.spec.Fields;
import net.andreho.aop.spec.Methods;
import net.andreho.aop.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to intercept reading access of a field
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Supports({
   Arg.class,
   Args.class,
   Arity.class,
   Current.class,
   Declaring.class,
   Intercepted.class,
   Marker.class,
   Line.class,
   Result.class,
   This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldGet {
   /**
    * Which methods, accessing wanted fields, should be processed
    *
    * @return
    */
   Methods[] methods() default {};

   /**
    * Which fields read-access should be intercepted
    *
    * @return
    */
   Fields[] fields() default {};
}
