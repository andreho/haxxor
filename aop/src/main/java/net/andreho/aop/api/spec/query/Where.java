package net.andreho.aop.api.spec.query;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A block of conditions that are bound with an AND-junction
 * <br/>Created by a.hofmann on 25.05.2017 at 13:50.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {

  /**
   * @return a block of conditions linked with an <b>AND</b> junction
   */
  Condition[] value();
}
