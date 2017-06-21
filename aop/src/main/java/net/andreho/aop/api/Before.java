package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.CanInject;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Site;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@CanInject({
            Arg.class,
            Args.class,
            Arity.class,
            Declaring.class,
            Intercepted.class,
            Attribute.class,
            Line.class,
            This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.BEFORE)
public @interface Before {
  /**
   * @return an unique name of a globally available profile
   * @see Profile
   */
  String profile() default "";

  /**
   * @return
   */
  Site site() default Site.CALLEE;

  /**
   * Should we install the call to the corresponding before-advice inside of a try-(catch)/finally block or not.
   *
   * @return
   * @apiNote Applies only if your aspect-stack defines additional {@link Catch @Catch} and/or
   * {@link Finally @Finally} interceptors
   */
  boolean safely() default false;

  /**
   * Defines a set with methods that should be processed
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means that any method will be selected.
   */
  Methods[] methods() default {};
}
