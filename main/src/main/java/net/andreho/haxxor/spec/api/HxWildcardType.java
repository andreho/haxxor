package net.andreho.haxxor.spec.api;


import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxWildcardType
    extends HxGeneric<HxWildcardType> {

  List<HxGeneric> getUpperBounds();

  HxWildcardType setUpperBounds(List<HxGeneric> upperBounds);

  List<HxGeneric> getLowerBounds();

  HxWildcardType setLowerBounds(List<HxGeneric> lowerBounds);
}
