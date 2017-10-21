package net.andreho.aop.api;

import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Fields;
import net.andreho.aop.api.spec.Methods;
import net.andreho.aop.api.spec.Parameters;

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
   * Defines an unique name of this globally available profile
   * @return an unique name of this profile
   */
  String name();

  /**
   * Defines a set with classes that should be processed
   *
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any class will be selected
   * @see Aspect
   */
  Classes[] classes() default {};

  /**
   * Defines a set with methods that should be processed
   *
   * @return an array of method selectors
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any method/constructor will be selected
   * @see Before
   * @see After
   * @see Around
   * @see Finally
   * @see Access.Get
   * @see Access.Set
   */
  Methods[] methods() default {};

  /**
   * Defines a set with fields that should be processed
   *
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any field will be selected
   * @see Access.Get
   * @see Access.Set
   */
  Fields[] fields() default {};

  /**
   * Defines a set with parameters that should be processed
   * @return an array of parameters' selectors
   * @apiNote elements are bound via an OR (disjunction);
   * empty array means any parameter will be selected
   * @see ArgPassing
   */
  Parameters[] parameters() default {};
}
