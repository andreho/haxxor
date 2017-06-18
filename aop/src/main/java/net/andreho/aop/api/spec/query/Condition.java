package net.andreho.aop.api.spec.query;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Condition annotation that will be evaluated against an annotation candidate
 * <br/>Created by a.hofmann on 25.05.2017 at 13:50.
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

  /**
   * @return name of an annotation's attribute
      */
  String name();

  /**
   * @return value(s) used to evaluate the right side of the selected operator (if appropriate)
   */
  String[] value() default {};

  /**
   * @return operator to evaluate
   */
  Operator operator() default Operator.EQ;

  /**
   * Controls the negation of the extracted boolean value from this condition.
   *
   * @return <b>true</b> to negate this condition, <b>false</b> ot leave the evaluated result as it is
   */
  boolean negate() default false;

  /**
   * In some rare cases when the referenced property isn't present on the referenced annotation type,
   * there is still the possibility to evaluate this condition to the given property.
   *
   * @return evaluation value when the referenced annotation's property wasn't found
   * @implNote the value is going to be used as it is, without any negation, defaults to: <b>false</b>
   */
  boolean undefinedValue() default false;
}
