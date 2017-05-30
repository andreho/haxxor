package net.andreho.aop.transform.ordering;

/**
 * Allows to define the execution order of marked transformers
 * <br/>Created by a.hofmann on 29.05.2017 at 22:00.
 * @see net.andreho.aop.transform.Transformer
 */
public @interface Order {
   /**
    * The order index of the marked transformer. A <b>smaller value</b> leads to greater ordering priority.
    * @return the order index
    * @implSpec default value is the lowest possible value,
    * so any other value would lead to greater priority
    */
   int value() default Integer.MAX_VALUE;
}