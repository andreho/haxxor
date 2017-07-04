package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects the concrete argument that was passed to intercepted method call.<br/>
 * The selection is made by desired argument's index.
 * If the argument at the given index isn't available, then a default value will be injected:
 * <code>boolean = false</code>, <code>int = 0</code>, <code>Object = null</code>, etc.)
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {
  /**
   * @return the parameter's index to inject
   */
  int value() default -1;
}
