package net.andreho.aop.api;

import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a class as aspect.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 * @see Order
 */
//@Repeatable(Aspects.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
//  /**
//   * If this aspect is activated using an annotation and you want to inject it in some
//   * @return
//   */
//  Class<? extends Annotation> marker() default Annotation.class;
//  /**
//   * Where should be the aspect's annotation placed to activate this aspect
//   *
//   * @return a list with desired target scopes
//   */
//  Scope[] scope() default Scope.ALL;
//
//  /**
//   * Where to apply this aspect at <b>caller</b> or <b>callee</b> execution site
//   *
//   * @return
//   */
//  Site site() default Site.CALLEE;
//
//  /**
//   * Prefix for a new name of the intercepted method, that is going to receive actual code of this intercepted method.
//   * This means one intercepted method results in creation of a method copy with provided
//   * <code>prefix + method_name + suffix</code>.
//   *
//   * @return a prefix value for the new name of the original intercepted method
//   */
//  String prefix() default "__$";
//
//  /**
//   * Suffix for new name of the intercepted method, that is going to receive actual code of this intercepted method.
//   * This means one intercepted method results in creation of a method copy with provided
//   * <code>prefix + method_name + suffix</code>.
//   *
//   * @return a suffix value for the new name of the original intercepted method
//   */
//  String suffix() default "$__";

  /**
   * Additional parameters for this aspect
   * @return
   */
  Parameter[] parameters() default {};

  /**
   * Explicit selection of classes that need to be processed
   *
   * @return
   * @implNote this should speed-up the overall performance
   */
  Classes[] classes() default {}; //(... OR ...)

  /**
   * Allows to instantiate the matching aspect when it needed and use its public member methods as possible interceptors.
   * This annotation must be used inside of the matching aspect either on exactly one <b>public static</b> method
   * that returns the aspect's type or on exactly one <b>public constructor</b> of the matching aspect.
   * The static factory method must return a valid instance of the matching aspect.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
  @interface Factory {
  }
}

