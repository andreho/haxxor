package net.andreho.aop.api;

import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Fields;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Site;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:00.
 */
@Target(ElementType.TYPE)
@Repeatable(Profiles.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Profile {
  /**
   * @return
   */
  String name();

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
   * @return {@link Throwable} types of expected exceptions, that need to be handled by this aspect
   */
  Class<? extends Throwable>[] throwable() default {Throwable.class};

  /**
   * Defines a set with methods that should be processed
   *
   * @return an array of method selectors
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any method/constructor will be selected
   */
  Methods[] methods() default {};

  /**
   * Defines a set with fields that should be processed
   *
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any field will be selected
   */
  Fields[] fields() default {};

  /**
   * Defines a set with classes that should be processed
   *
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any class will be selected
   */
  Classes[] classes() default {};
}
