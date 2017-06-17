package net.andreho.aop.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Methods {

  /**
   * @return a selector for possible declaring classes or interfaces
   */
  Classes[] declaredBy() default {};
  //AND

  /**
   * Selection of methods with given modifiers
   *
   * @return
   */
  Modifier[] modifiers() default {};
  //AND

  /**
   * Selects methods with matching name
   *
   * @return selection patterns for match methods by their name
   */
  Named[] named() default {};
  //AND

  /**
   * Selection of methods based on their return type
   *
   * @return
   */
  Classes[] returning() default {};
  //AND

  /**
   * Selection of methods that are annotated with selected annotations
   *
   * @return
   */
  Annotated[] annotated() default {};
  //AND

  /**
   * Selection of methods based on their thrown exceptions
   *
   * @return
   */
  Classes[] throwing() default {};
  //AND

  /**
   * Selection of methods that have exactly provided signatures
   *
   * @return
   */
  Signatures[] signatures() default {};
  //AND

  /**
   * Selection of methods that await any of selected parameter's references
   *
   * @return
   */
  Parameters[] parameters() default {};

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
