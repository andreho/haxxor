package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Current;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.CanInject;
import net.andreho.aop.api.spec.Fields;
import net.andreho.aop.api.spec.Methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

  /**
   * Allows to intercept reading access of a field
   * <br/>Created by a.hofmann on 18.09.2015.<br/>
   */
  @CanInject({
               Arg.class,
               Args.class,
               Arity.class,
               Current.class,
               Declaring.class,
               Intercepted.class,
               Attribute.class,
               Line.class,
               Result.class,
               This.class})
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Get {
    /**
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String profile() default "";

    /**
     * Which read-access should be intercepted
     *
     * @return
     */
    Fields[] fields() default {};

    /**
     * Which methods, accessing wanted fields, should be processed
     *
     * @return
     */
    Methods[] methods() default {};
  }

  /**
   * Allows an aspect to intercept writing access of a field
   * <br/>Created by a.hofmann on 18.09.2015.<br/>
   */
  @CanInject({
               Arg.class,
               Args.class,
               Arity.class,
               Current.class,
               Declaring.class,
               Intercepted.class,
               Attribute.class,
               Line.class,
               Result.class,
               This.class})
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Set {
    /**
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String profile() default "";

    /**
     * Which fields should be processed
     *
     * @return
     */
    Fields[] fields() default {};

    /**
     * Which methods accessing wanted field should be processed
     *
     * @return
     */
    Methods[] methods() default {};
  }
}
