package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxWildcardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.andreho.haxxor.Utils.isUninitialized;

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
  public HxWildcardTypeImpl setUpperBounds(final List<HxGeneric> upperBounds) {
    this.upperBounds = upperBounds;
    return this;
  }

  public HxWildcardTypeImpl addUpperBound(HxGeneric<?> bound) {
    if (isUninitialized(upperBounds)) {
      upperBounds = new ArrayList<>(1);
    }
    getUpperBounds().add(minimize(bound));
    return this;
  }

  @Override
  public List<HxGeneric> getLowerBounds() {
    return lowerBounds;
  }

  @Override
  public HxWildcardTypeImpl setLowerBounds(final List<HxGeneric> lowerBounds) {
    this.lowerBounds = lowerBounds;
    return this;
  }

  public HxWildcardTypeImpl addLowerBound(HxGeneric<?> bound) {
    if (isUninitialized(lowerBounds)) {
      lowerBounds = new ArrayList<>(1);
    }
    getLowerBounds().add(minimize(bound));
    return this;
  }

  public HxWildcardTypeImpl asUnboundType(Haxxor haxxor) {
    addUpperBound(haxxor.reference("java.lang.Object"));
    this.lowerBounds = Collections.emptyList();
    return this;
  }
}
