package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Supports;

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
//  Marker.class,
  Line.class,
  Result.class,
  This.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Before {

  /**
   * Should we install the call to the corresponding before-advice inside of a try-(catch)/finally block or not.
   * Applies only if your aspect defines additional {@link Catch @Catch} and/or {@link Finally @Finally} interceptors
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
