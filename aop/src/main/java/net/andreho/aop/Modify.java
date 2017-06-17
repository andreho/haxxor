package net.andreho.aop;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:23.
 */

import net.andreho.aop.spec.Classes;
import net.andreho.aop.spec.Fields;
import net.andreho.aop.spec.Methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Modify {

  /**
   * This annotations allows to modify the corresponding representation of a class directly.
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
   * This annotations allows to modify the corresponding representation of a field directly.
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
   * This annotations allows to modify the corresponding representation of a method directly.
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
