package net.andreho.aop.spec.query;

/**
 * A block of conditions that are bound with an AND-junction
 * <br/>Created by a.hofmann on 25.05.2017 at 13:50.
 */
public @interface Where {
   /**
    * @return a block of conditions linked with an <b>AND</b> junction
    */
   Condition[] value();
}
