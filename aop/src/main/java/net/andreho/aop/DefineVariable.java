package net.andreho.aop;

import net.andreho.aop.injectable.Variable;

/**
 * Allows to allocate the returned value of the interceptor method call as new local variable.
 * You may then reuse it for further outstanding interception calls.
 * <br/>Created by a.hofmann on 02.06.2017 at 22:35.
 * @see Variable
 */
public @interface DefineVariable {

  /**
   * This attribute defines a unique name within currently intercepted execution context (method/constructor) to be
   * used as alias for possible further injection into outstanding interceptor calls
   * @return the uniquely identifiable name of a new local variable
   * @see Variable
   */
  String value();
}
