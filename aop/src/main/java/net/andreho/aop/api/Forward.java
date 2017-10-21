package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Marker;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
 */
@Supports(injectionOf = {
             Arg.class,
             Args.class,
             Arity.class,
             Declaring.class,
             Intercepted.class,
             Marker.class,
             Line.class,
             Result.class,
             This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.FORWARDING)
@Deprecated /* NOT SUPPORTED AT THE MOMENT */
public @interface Forward {

  /**
   * @return an unique name of a globally available profile
   * @see Profile
   */
  String value();
}
