package net.andreho.haxxor.spec.generics;


import net.andreho.haxxor.spec.api.HxGeneric;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxWildcardType
    extends HxGeneric {

  HxGeneric[] getUpperBounds();

  HxGeneric[] getLowerBounds();
}
