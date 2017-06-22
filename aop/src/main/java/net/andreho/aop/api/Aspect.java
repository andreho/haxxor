package net.andreho.aop.api;

import net.andreho.aop.api.spec.CanInject;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a class as aspect.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 *
 * @see Order
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

  /**
   * Allows to instantiate the matching aspect when it needed and use its public member methods as possible
   * interceptors.
   * This annotation must be used inside of the matching aspect on exactly one <b>public static</b> method
   * that returns the aspect's type. The static factory method must return a valid instance of the matching aspect.
   */
  @CanInject({/* DEPENDS ON ASPECT'S TYPE */})
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
  @interface Factory {

    /**
     * @return <b>true</b> if the returned aspect instance must be saved locally and reused for later usage;
     * <b>false</b> to fetch factory-executable each time it's needed
     */
    boolean reuse() default true;
  }

  /**
   * Additional parameters for this aspect
   *
   * @return
   */
  Parameter[] parameters() default {};

  /**
   * Explicit pre-filter of classes that need to be processed by this aspect definition
   *
   * @return
   * @implNote this should speed-up the overall performance
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any loaded classes will be selected at this stage
   */
  Classes[] classes() default {}; //(... OR ...)
}

