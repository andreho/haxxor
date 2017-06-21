package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Result;
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
            Declaring.class,
            Intercepted.class,
            Caught.class,
            Attribute.class,
            Line.class,
            Result.class,
            This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.FINALLY)
public @interface Finally {
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
   * Which methods should be processed
   * @return
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any field will be selected
   */
  Methods[] methods() default {};
}
