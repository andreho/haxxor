package net.andreho.aop.api;


import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 05.10.2017 at 11:33.
 */
@Supports(
  injectionOf = {
    Arg.class,
    Arity.class,
    Attribute.class,
    Declaring.class,
    Intercepted.class,
    This.class
  },
  postProcessingWith = {
    Attribute.class,
    Redefine.class
  })
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.ARG_PASSING)
public @interface ArgPassing {

  /**
   * @return an unique name of a globally available profile
   * @see Profile
   */
  String value();
}
