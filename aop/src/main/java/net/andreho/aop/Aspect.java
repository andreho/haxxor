package net.andreho.aop;

import net.andreho.aop.spec.Classes;
import net.andreho.aop.spec.Parameter;
import net.andreho.aop.spec.Scope;
import net.andreho.aop.spec.Site;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a class as aspect.
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 * @see Order
 */
@Repeatable(Aspects.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

  /**
   * Additional parameters for this aspect
   * @return
   */
  Parameter[] parameters() default {};

  /**
   * Prefix for a new name of the intercepted method, that is going to receive actual code of this intercepted method.
   * This means one intercepted method results in creation of a method copy with provided
   * <code>prefix + method_name + suffix</code>.
   *
   * @return a prefix value for the new name of the original intercepted method
   */
  String prefix() default "__$";

  /**
   * Suffix for new name of the intercepted method, that is going to receive actual code of this intercepted method.
   * This means one intercepted method results in creation of a method copy with provided
   * <code>prefix + method_name + suffix</code>.
   *
   * @return a suffix value for the new name of the original intercepted method
   */
  String suffix() default "$__";

  /**
   * Where should be the aspect's annotation placed to activate this aspect
   *
   * @return a list with desired target scopes
   */
  Scope[] scope() default Scope.ALL;

  /**
   * Where to apply this aspect at <b>caller</b> or <b>callee</b> execution site
   *
   * @return
   */
  Site site() default Site.CALLEE;

  /**
   * Explicit selection of classes that need to be processed
   *
   * @return
   * @implNote this should speed-up the overall performance
   */
  Classes[] classes() default {};
}

