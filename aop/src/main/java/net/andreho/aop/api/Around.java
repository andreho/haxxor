package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Original;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 02.07.2017 at 21:17.
 */
@Supports(injectionOf = {
             Arg.class,
             Args.class,
             Arity.class,
             Attribute.class,
             Declaring.class,
             Intercepted.class,
             Original.class,
             Line.class,
             This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.AROUND)
@Deprecated /* NOT SUPPORTED AT THE MOMENT */
public @interface Around {
  /**
   * @return an unique name of a globally available profile
   * @see Profile
   */
  String value();
}
