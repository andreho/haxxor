package net.andreho.haxxor.spec.api;


import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxWildcardType
    extends HxGenericElement<HxWildcardType> {

  List<HxGenericElement> getUpperBounds();

  HxWildcardType setUpperBounds(List<HxGenericElement> upperBounds);

  List<HxGenericElement> getLowerBounds();

  HxWildcardType setLowerBounds(List<HxGenericElement> lowerBounds);
}
