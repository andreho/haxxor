package net.andreho.aop.api;

import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.Parameter;
import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a <b>public class</b> as aspect.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 *
 * @see Order
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Order(StandardOrder.ASPECT)
public @interface Aspect {

  /**
   * Allows to instantiate the matching aspect when it needed and use its public member methods as possible
   * interceptors.
   * This annotation must be used inside of the matching aspect on exactly one <b>public static</b> method
   * that returns the aspect's type. The static factory method must return a valid instance of the matching aspect.
   */
  @Supports(
    injectionOf = {
      Line.class,
      Arity.class,
      Declaring.class,
      Intercepted.class,
      This.class
    }, postProcessingWith = {
      Attribute.class
  })
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
  @interface Factory {

    /**
     * @return <b>true</b> if the returned aspect instance must be saved as local variable
     * and reused for possible later usage;
     * <b>false</b> to fetch aspect-factory each time as it's needed
     */
    boolean reuse() default true;
  }

  /**
   * @return an unique name of a globally available profile
   * @see Profile
   */
  String value();

  /**
   * Additional parameters for this aspect
   *
   * @return
   */
  Parameter[] parameters() default {};
}

