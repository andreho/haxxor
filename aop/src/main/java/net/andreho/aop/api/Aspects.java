package net.andreho.aop.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Status: <b>DISABLED</b> see: Aspect.java
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspects {

  /**
   * @return
   */
  Aspect[] value();
}
