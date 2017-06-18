package net.andreho.aop.api.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public @interface Fields {
  /**
   * Selection of fields with given modifiers
   *
   * @return an array with specific modifiers
   * @implNote by default matches any modifiers
   */
  Modifier[] modifiers() default {};
  //AND

  /**
   * Selects fields that was declared by selected types
   *
   * @return an array with class selectors
   * @implNote empty array means any classes
   */
  Classes[] declaredBy() default {};
  //AND

  /**
   * Selection of fields that have provided types.<br/>
   *
   * @return an array with matching class references
   * @implNote empty array means any classes
   */
  Classes[] typed() default {};
  //AND

  /**
   * Selects fields that are annotated with given annotations.<br/>
   *
   * @return an array with annotations
   * @implNote empty array means any or without annotations
   */
  Annotated[] annotated() default {};
  //AND

  /**
   * Selects fields with matching names
   *
   * @return an array with name selectors
   * @implNote empty array means any names
   */
  Named[] named() default {};

  /**
   * Allows to inverse this selection
   * @return <b>true</b> to inverse this selection, <b>false</b> to leave it as it is
   */
  boolean negate() default false;
}
