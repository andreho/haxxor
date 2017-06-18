package net.andreho.aop.api.spec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Memorizes applied chagnes and
 * what kind of work was done on the annotated element
 * <br/>Created by a.hofmann on 26.09.2015.<br/>
 */
@Repeatable(Changes.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
public @interface Change {

  /**
   * @return action code
   */
  String value();

  /**
   * @return timestamp of this change
   */
  long timestamp();

  /**
   * @return
   */
  ChangeKind kind() default ChangeKind.CHANGED;
}
