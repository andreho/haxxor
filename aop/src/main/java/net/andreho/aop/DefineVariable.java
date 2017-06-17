package net.andreho.aop;

import net.andreho.aop.injectable.Variable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to allocate the returned value of the interceptor method call as new local variable.
 * You may then reuse it for further outstanding interception calls.
 * <br/>Created by a.hofmann on 02.06.2017 at 22:35.
 * @see Variable
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefineVariable {

  /**
   * This attribute defines a unique name within currently intercepted execution context (method/constructor) to be
   * used as alias for possible further injection into outstanding interceptor calls
   * @return the uniquely identifiable name of a new local variable
   * @see Variable
   */
  String value();
}
