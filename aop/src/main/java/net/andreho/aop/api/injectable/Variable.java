package net.andreho.aop.api.injectable;

import net.andreho.aop.api.DefineVariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation injects the value of previously allocated local variable via {@link DefineVariable}
 * <br/>Created by a.hofmann on 02.06.2017 at 22:33.
 * @see DefineVariable
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Variable {

  /**
   * This attribute must define the name of the previously defined local-variable
   * @return an identifiable name of the referenced local-variable
   * @see DefineVariable
   */
  String value();
}
