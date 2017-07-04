package net.andreho.aop.api.spec;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a marker annotation and has none special meaning.
 * Created by a.hofmann on 19.09.2015.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Supports {

  /**
   * @return a list with supported injectable parameters
   */
  Class<? extends Annotation>[] injectionOf() default {};

  /**
   * @return a list with supported post-advice-call processors
   */
  Class<? extends Annotation>[] postProcessingWith() default {};
}
