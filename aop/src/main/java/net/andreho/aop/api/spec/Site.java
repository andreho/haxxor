package net.andreho.aop.api.spec;

/**
 * Allows to control where selected aspect should be applied, either at callee or caller site.
 * Callee site is most flexible and secure because
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public enum Site {
  /**
   * Applies aspect at callee site - direct after the invocation of intercepted method/constructor itself
   */
  CALLEE,
//    CALLER //comes possibly in future
}
