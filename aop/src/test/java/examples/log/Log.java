package examples.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br/>Created by a.hofmann on 23.03.2017 at 19:35.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

  /**
   * @return name of the static final field that should be created and contain the reference to the desired log itself
   */
  String value() default "LOG";
}
