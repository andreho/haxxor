package net.andreho.aop.modificator;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 24.03.2017 at 00:26.
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
