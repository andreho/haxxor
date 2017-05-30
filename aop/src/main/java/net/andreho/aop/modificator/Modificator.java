package net.andreho.aop.modificator;

import net.andreho.haxxor.spec.HxType;

/**
 * <br/>Created by a.hofmann on 24.03.2017 at 00:26.
 */
/*
@Supports({
   Mutable.class,
   Declaring.class,
   Marker.class
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
*/
public interface Modificator {
   /**
    * @param reference
    * @return
    */
   boolean applicable(final HxType reference);

   /**
    * @param type
    * @return
    */
   HxType modify(final HxType type);
}
