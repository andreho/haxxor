package net.andreho.haxxor.api.stub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Stub {
  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE_USE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ignore {}

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Override {}

  @Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Autowire {
    boolean strict() default false;
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Before {}

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface After {}

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Catch {}

  /**
   * The marked method must return a {@link Class} instance and doesn't consume any arguments
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface DeclaringClass {}

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Constructor {}
  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Method {}

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Named {
    String value() default "";
  }

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Super {
    /**
     * @return prefix value of the referenced method's name
     */
    String value() default "super_";
  }

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Original {
    /**
     * @return prefix value of the referenced method's name
     */
    String value() default "original_";
  }
}
