package net.andreho.aop.api.spec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Disables listed aspects, so they aren't applied to the marked elements.
 * <br/>Created by a.hofmann on 21.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,
         ElementType.METHOD,
         ElementType.FIELD,
         ElementType.CONSTRUCTOR,
         ElementType.PARAMETER})
public @interface Disable {

  /**
   * @return list with aspect's classes that need to be disabled for marked element
   */
  Class<?>[] value();
}
