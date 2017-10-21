package net.andreho.aop.api.mixin;

import net.andreho.aop.api.Order;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.StandardOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 30.09.2017 at 01:02.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mixin {

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MIXIN + 1000)
  @interface Applications {
    Application[] value();
  }

  /**
   */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Order(StandardOrder.MIXIN)
  @Repeatable(Applications.class)
  @interface Application {
    /**
     * Defines the name of a specific selection profile for target classes,
     * where selected {@link #mixins()} must be applied
     * @return an unique name of a globally available profile
     * @see Profile
     */
    String value();

    /**
     * Defines a set of mixin types, that must be applied if a class will be matched by the referenced profile
     * @return a set of classes that have an applicable layout for a mixin type
     * @see Mixin
     */
    Class<?>[] mixins();
  }

  /**
   */
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ignore {
  }

  /**
   */
  @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Forced {
  }

  /**
   */
  @Target(ElementType.PARAMETER)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Parameter {
    String value();
  }

//  /**
//   */
//  @Target(ElementType.METHOD)
//  @Retention(RetentionPolicy.RUNTIME)
//  @interface Strategy {
//    Mode value() default Mode.INSERT;
//  }
//
//  /**
//   */
//  enum Mode {
//    INSERT,
//    SUBSTITUTE,
////    MERGE
//  }
}
