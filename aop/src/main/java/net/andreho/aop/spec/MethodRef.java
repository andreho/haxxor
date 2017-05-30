package net.andreho.aop.spec;

/**
 * <br/>Created by a.hofmann on 18.09.2015.<br/>
 */
public @interface MethodRef {
   /**
    * @return a selector for possible declaring classes or interfaces
    */
   ClassRef[] declaredBy() default {};

   /**
    * Selection of methods with given modifiers
    *
    * @return
    */
   Modifier[] modifiers() default {};

   /**
    * Selects methods with matching name
    *
    * @return selection patterns for match methods by their name
    */
   Named[] named() default {};

   /**
    * Selection of methods that are annotated with selected annotations
    *
    * @return
    */
   Annotated[] annotated() default {};

   /**
    * Selection of methods based on their return type
    *
    * @return
    */
   ClassRef[] returning() default {};

   /**
    * Selection of methods based on their thrown exceptions
    *
    * @return
    */
   ClassRef[] throwing() default {};

   /**
    * Selection of methods that have exactly provided signatures
    *
    * @return
    */
   SignatureRef[] signatures() default {};

   /**
    * Selection of methods that await any of selected parameter's references
    *
    * @return
    */
   ParamRef[] parameters() default {};
}
