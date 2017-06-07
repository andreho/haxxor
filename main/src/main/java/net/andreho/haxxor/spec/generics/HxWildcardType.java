package net.andreho.haxxor.spec.generics;


import net.andreho.haxxor.spec.api.HxGeneric;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxWildcardType
    extends HxGeneric<HxWildcardType> {

  List<HxGeneric> getUpperBounds();

  List<HxGeneric> getLowerBounds();
}
