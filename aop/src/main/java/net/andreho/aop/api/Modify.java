package net.andreho.aop.api;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 04:23.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Modify {

  /**
   * This annotations allows to modify the corresponding representation of selected classes directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.api.HxType)boolean</code>.<br/>
   * If the annotated method returns <b>true</b> then
   * it means that the given type was modified and must be reassembled.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   *
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_TYPE)
  @interface Type {

    /**
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String value() default "*";
  }

  /**
   * This annotations allows to modify the corresponding representation of selected fields directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.api.HxField)boolean</code>.<br/>
   * If the annotated method returns <b>true</b> then
   * it means that the given field was modified and must be reassembled.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   *
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_FIELD)
  @interface Field {

    /**
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String value() default "*";
  }

  /**
   * This annotations allows to modify the corresponding representation of selected methods directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.api.HxMethod)boolean</code>.<br/>
   * If the annotated method returns <b>true</b> then
   * it means that the given method was modified and must be reassembled.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   *
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_METHOD)
  @interface Method {

    /**
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String value() default "*";
  }
}
