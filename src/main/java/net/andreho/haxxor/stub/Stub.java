package net.andreho.haxxor.stub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Stub {
  /**
   * Defines a set of classes and interfaces, that must be realized by the target class.
   * @return a set with classes
   */
  java.lang.Class<?>[] requires() default {};

  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE_USE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ignore {}

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Override {}

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.PARAMETER, ElementType.METHOD}) //ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR,
  @Retention(RetentionPolicy.RUNTIME)
  @interface Named {
    String value() default "";
  }

  /**
   * The marked <b>static</b> method must return a {@link java.lang.Class} instance and doesn't consume any arguments
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Class {}

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Method {
    /**
     * @return prefix value of the referenced method's name
     */
    String value() default "";
  }

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Field {
    /**
     * @return prefix value of the referenced field's name
     */
    String value() default "";
  }
}
