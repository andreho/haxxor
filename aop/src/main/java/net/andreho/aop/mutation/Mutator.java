package net.andreho.aop.mutation;

import net.andreho.haxxor.Haxxor;
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
public interface Mutator {

   /**
    * @param haxxor
    * @param reference
    * @return
    */
   boolean isApplicableFor(final Haxxor haxxor,
                           final HxType reference);


   /**
    * @param haxxor
    * @param type
    * @return
    */
   byte[] apply(final Haxxor haxxor, final HxType type);
}
