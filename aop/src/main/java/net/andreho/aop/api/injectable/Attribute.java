package net.andreho.aop.api.injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is a marker annotation for either an injectable (possibly generated) element or
 * a parameter receiver that automatically receives value of the equally marked class element (field or not-void, no-arg method).
 * The matching is done via equality checking of element's {@link #value() names}.
 * <br/>Created by a.hofmann on 16.06.2017 at 04:19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
public @interface Attribute {

  /**
   * @return name of the marked attribute
   */
  String value();

  /**
   * @return <b>true</b> if this attribute must be visible for sub-classes, <b>false</b> otherwise.
   * @apiNote if set to <b>true</b> the user must ensure
   * that the marked element has proper visibility-level for intercepted method's or constructor's call.
   */
  boolean inheritable() default false;
}
