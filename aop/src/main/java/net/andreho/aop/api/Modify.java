package net.andreho.aop.api;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:23.
 */

import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Fields;
import net.andreho.aop.api.spec.Methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Modify {

  /**
   * This annotations allows to modify the corresponding representation of selected classes directly.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_TYPE)
  @interface Type {
    /**
     * Selection of classes that need to be processed
     * @return
     */
    Classes[] value() default {};
  }

  /**
   * This annotations allows to modify the corresponding representation of selected fields directly.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_FIELD)
  @interface Field {
    /**
     * Selection of fields that need to be processed
     * @return
     */
    Fields[] value() default {};
  }

  /**
   * This annotations allows to modify the corresponding representation of selected methods directly.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_METHOD)
  @interface Method {
    /**
     * Selection of methods/constructors that need to be processed
     * @return
     */
    Methods[] value() default {};
  }
}
