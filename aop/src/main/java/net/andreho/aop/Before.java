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
   * Applies only if your aspect defines @Catch and/or @Finally interceptors
   *
   * @return
   */
  boolean safely() default false;

  /**
   * Defines a set with methods that should be processed
   *
   * @return
   * @implNote elements are bound via an OR (disjunction)
   * @implSpec empty array means no selection at all
   */
  Methods[] methods() default {};
}
