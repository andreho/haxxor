package net.andreho.aop.api.spec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Methods {

  /**
   * This is a rough preselection filter based on method's type
   * @return what sort of methods must be selected
   */
  MethodsType type() default MethodsType.METHODS;
  //AND

  /**
   * @return a selector for possible declaring classes or interfaces
   * @apiNote elements are bound via an OR (disjunction)
   */
  Classes[] declaredBy() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods with given modifiers
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Modifier[] modifiers() default {}; //(... OR ...)
  //AND

  /**
   * Selects methods with matching name
   *
   * @return selection patterns for match methods by their name
   * @apiNote elements are bound via an OR (disjunction)
   */
  Named[] named() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods based on their return type
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Classes[] returning() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods that are annotated with selected annotations
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Annotated[] annotated() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods based on their thrown exceptions
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Classes[] throwing() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods that have exactly provided signatures
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Signatures[] signatures() default {}; //(... OR ...)
  //AND

  /**
   * Selection of methods that await any of selected parameter's references
   *
   * @return
   * @apiNote elements are bound via an OR (disjunction)
   */
  Parameters[] parameters() default {}; //(... OR ...)

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
