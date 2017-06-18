package net.andreho.aop.api;

import net.andreho.aop.api.spec.Supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to replace the code of the referenced method or constructor.
 * <br/>Created by a.hofmann on 07.06.2017 at 22:44.
 */
@Supports({})
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Patch {

  Class target();

  String name();

  Signature signature() default @Signature;

  @interface Class {

    java.lang.Class<?> value() default void.class;

    String name() default "void";
  }

  @interface Signature {
    Class[] parameters() default {};
    Class returns() default @Class;
  }

  @Target({ElementType.TYPE,
           ElementType.FIELD,
           ElementType.METHOD,
           ElementType.CONSTRUCTOR,
           ElementType.PARAMETER
          })
  @interface Stub {
  }
}
