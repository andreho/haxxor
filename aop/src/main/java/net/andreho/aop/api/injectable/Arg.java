package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker to inject a concrete argument that was passed to intercepted method call.<br/>
 * The selection is either made by desired argument's index or
 * alternatively by most suitable type if the {@link #value()} was set to {@link #BY_TYPE}.<br/>
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
  int value() default BY_TYPE;
//
//  /**
//   * @return <b>true</b> if injection of the marked parameter must be fully completed in order
//   * to execute following interceptor, <b>false</b> to use a suitable default value and call the interceptor anyway
//   */
//  boolean required() default false;

  /**
   * Constant to select arguments by their type
   */
  int BY_TYPE = Integer.MIN_VALUE;
}
