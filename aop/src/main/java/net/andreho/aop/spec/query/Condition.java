package net.andreho.aop.spec.query;

/**
 * Condition annotation that will be evaluated against an annotation candidate
 * <br/>Created by a.hofmann on 25.05.2017 at 13:50.
 */
public @interface Condition {
   /**
    * @return name of an annotation's property
    */
   String name();

   /**
    * @return value for selected operator
    */
   String[] value() default {};

   /**
    * @return operator to evaluate
    */
   Op operator() default Op.EQ;

   /**
    * Controls the negation of the extracted boolean value from this condition.
    *
    * @return <b>true</b> to negate this condition, <b>false</b> ot leave the evaluated result as it is
    */
   boolean negate() default false;

   /**
    * In some rare cases when the referenced property isn't present on the referenced annotation type,
    * there is still the possibility to evaluate the condition using this property.
    *
    * @return evaluation value when the referenced annotation's property wasn't found
    * @implNote the value is going to be used as it is, without any negation, defaults to: <b>false</b>
    */
   boolean undefinedValue() default false;
}
