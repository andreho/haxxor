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

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Modify {

  /**
   * This annotations allows to modify the corresponding representation of selected classes directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.spec.api.HxType)boolean</code>.
   * If the annotated method returns <b>true</b> then it means that the given type was modified.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_TYPE)
  @interface Type {
    /**
     * @return an unique name of a globally available profile
     */
    String profile() default "";

    /**
     * Selection of classes that need to be processed
     * @apiNote elements are bound via an OR (disjunction);
     * empty array means any class will be selected
     */
    Classes[] value() default {};
  }

  /**
   * This annotations allows to modify the corresponding representation of selected fields directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.spec.api.HxField)boolean</code>.
   * If the annotated method returns <b>true</b> then it means that the given field was modified.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_FIELD)
  @interface Field {
    /**
     * @return an unique name of a globally available profile
     */
    String profile() default "";

    /**
     * Selection of fields that need to be processed
     * @apiNote elements are bound via an OR (disjunction);
     * empty array means any fields will be selected
     */
    Fields[] value() default {};
  }

  /**
   * This annotations allows to modify the corresponding representation of selected methods directly.
   * <br/><b>ATTENTION:</b> The annotated method <b>must</b> have following signature:
   * <code>(net.andreho.haxxor.spec.api.HxMethod)boolean</code>.
   * If the annotated method returns <b>true</b> then it means that the given method was modified.
   * <br/>Created by a.hofmann on 16.06.2017 at 04:12.
   * @see Order
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MODIFY_METHOD)
  @interface Method {
    /**
     * @return an unique name of a globally available profile
     */
    String profile() default "";

    /**
     * Selection of methods/constructors that need to be processed
     * @apiNote elements are bound via an OR (disjunction);
     * empty array means any methods will be selected
     */
    Methods[] value() default {};
  }
}
