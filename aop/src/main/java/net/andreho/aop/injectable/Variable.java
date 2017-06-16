package net.andreho.aop.injectable;

import net.andreho.aop.DefineVariable;

/**
 * This annotation injects the value of previously allocated local variable via {@link DefineVariable}
 * <br/>Created by a.hofmann on 02.06.2017 at 22:33.
 * @see DefineVariable
 */
public @interface Variable {

  /**
   * This attribute must define the name of the previously defined local-variable
   * @return an identifiable name of the referenced local-variable
   * @see DefineVariable
   */
  String value();
}
