package net.andreho.aop.api.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public enum Scope {
  /**
   * Enables desired aspect for all possible elements
   */
  ALL,
  /**
   * Enables desired aspect for all classes located in the selected package
   */
  PACKAGE,
  /**
   * Enables desired aspect for marked type
   */
  TYPE,
  /**
   * Enables desired aspect for marked constructor
   */
  CONSTRUCTOR,
  /**
   * Enables desired aspect for marked field (including interception of both access-types: Read/Write)
   */
  FIELD,
  /**
   * Enables desired aspect for marked method
   */
  METHOD,
  /**
   * Enables desired aspect for marked parameter and its method/constructor
   */
  PARAMETER
}
