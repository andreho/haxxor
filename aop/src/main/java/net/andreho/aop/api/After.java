package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.injectable.Variable;
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
            Variable.class,
            Line.class,
            Result.class,
            This.class})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
  /**
   * @return an unique name of a globally available profile
   */
  String profile() default "";

  /**
   * @return
   */
  Site site() default Site.CALLEE;

  /**
   * Defines a set with methods that should be processed
   *
   * @return an array of method selectors
   * @apiNote elements are bound via an OR (disjunction)
   * @implSpec empty array means no selection at all
   */
  Methods[] methods() default {};
}
