package net.andreho.haxxor.spec.generics.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.generics.HxWildcardType;

import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 07.06.2017 at 02:46.
 */
public class HxWildcardTypeImpl
    extends HxAbstractGeneric<HxWildcardType>
    implements HxWildcardType {

  private List<HxGeneric> upperBounds = Collections.emptyList();
  private List<HxGeneric> lowerBounds = Collections.emptyList();

  public HxWildcardTypeImpl() {
  }

  @Override
  public List<HxGeneric> getUpperBounds() {
    return upperBounds;
  }

  @Override
  public List<HxGeneric> getLowerBounds() {
    return lowerBounds;
  }

  @Override
  public HxGeneric attach(final HxGeneric generic) {
    return this;
  }
}
