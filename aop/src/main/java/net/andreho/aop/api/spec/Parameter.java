package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 15:19.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

  /**
   * @return name of this parameter
   */
  String name();

  /**
   * @return value of this parameter
   */
  String[] value() default {};
}
