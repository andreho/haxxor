package net.andreho.aop;

import net.andreho.aop.injectable.Arg;
import net.andreho.aop.injectable.Args;
import net.andreho.aop.injectable.Arity;
import net.andreho.aop.injectable.Declaring;
import net.andreho.aop.injectable.Intercepted;
import net.andreho.aop.injectable.Line;
import net.andreho.aop.injectable.Marker;
import net.andreho.aop.injectable.Result;
import net.andreho.aop.injectable.This;
import net.andreho.aop.spec.MethodRef;
import net.andreho.aop.spec.ParamRef;
import net.andreho.aop.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
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
   Result.class,
   This.class})
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
   /**
    * Should we include the call of before-advice inside of a try-(catch)/finally block or not.
    * Applies only if your aspect supports @Catch and/or @Finally
    *
    * @return
    */
   boolean finalized() default false;

   /**
    * Which methods should be processed
    *
    * @return
    */
   MethodRef[] methods() default {};

   /**
    * Special parameters (currently none)
    *
    * @return
    */
   ParamRef[] parameters() default {};
}
