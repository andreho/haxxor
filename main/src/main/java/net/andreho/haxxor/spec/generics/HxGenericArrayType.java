package net.andreho.haxxor.spec.generics;

import net.andreho.haxxor.spec.HxGeneric;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxGenericArrayType extends HxGeneric {
   /**
    * @return either an instance of HxType, HxParameteriziedType, HxTypeVariable
    */
   HxGeneric getGenericComponentType();

   HxGenericArrayType setGenericComponentType(final HxGeneric genericComponentType);
}
