package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public @interface FieldRef {
   /**
    * Selects fields that was declared by selected types
    *
    * @return an array with class selectors
    * @implNote empty array means any classes
    */
   ClassRef[] declaredBy() default {};

   /**
    * Selection of fields that have provided types.<br/>
    * @return an array with matching class references
    * @implNote empty array means any classes
    */
   ClassRef[] typed() default {};

   /**
    * Selects fields with matching names
    *
    * @return an array with name selectors
    * @implNote empty array means any names
    */
   Named[] named() default {};

   /**
    * Selection of fields with given modifiers
    * @return an array with specific modifiers
    * @implNote by default matches any modifiers
    */
   Modifier[] modifiers() default {};

   /**
    * Selects fields that are annotated with given annotations.<br/>
    * @return an array with annotations
    * @implNote empty array means any or without annotations
    */
   Annotated[] annotated() default {};

}
