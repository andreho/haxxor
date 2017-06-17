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
import net.andreho.aop.spec.Methods;
import net.andreho.aop.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.FORWARDING)
public @interface Forwarding {

  /**
   * @return
   */
  Methods[] value();
}
