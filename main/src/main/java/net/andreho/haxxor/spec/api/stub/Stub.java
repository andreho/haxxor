package net.andreho.haxxor.spec.api.stub;

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
  Element value();

  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ignore {}

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Override {}

  @Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Autowire {}

//  @Target(ElementType.PARAMETER)
//  @Retention(RetentionPolicy.RUNTIME)
//  @interface This {}
//
//  @Target(ElementType.PARAMETER)
//  @Retention(RetentionPolicy.RUNTIME)
//  @interface Named {
//    int value() default -1;
//  }
//
//  @Target(ElementType.PARAMETER)
//  @Retention(RetentionPolicy.RUNTIME)
//  @interface Args {
//  }
//
//  @Target(ElementType.PARAMETER)
//  @Retention(RetentionPolicy.RUNTIME)
//  @interface Return {}
  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface DeclaringClass {
  }

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Constructor {
  }
  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Method {
  }

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
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Indexed {
    int value();
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
  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Original {
    /**
     * @return prefix value of the referenced method's name
     */
    String value() default "original_";
  }

  /**
   * <br/>Created by a.hofmann on 13.10.2017 at 22:20.
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Reference {
    String name();
    Class<?> returns() default void.class;
    Class<?>[] parameters() default {};
  }
}
