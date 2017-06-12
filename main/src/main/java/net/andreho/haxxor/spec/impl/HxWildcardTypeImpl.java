package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxGenericElement;
import net.andreho.haxxor.spec.api.HxType;
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

  private List<HxGenericElement> upperBounds = Collections.emptyList();
  private List<HxGenericElement> lowerBounds = Collections.emptyList();

  public HxWildcardTypeImpl() {
  }

  @Override
  public List<HxGenericElement> getUpperBounds() {
    return upperBounds;
  }

  @Override
  public HxWildcardTypeImpl setUpperBounds(final List<HxGenericElement> upperBounds) {
    this.upperBounds = upperBounds;
    return this;
  }

  public HxWildcardTypeImpl addUpperBound(HxGenericElement<?> bound) {
    if (isUninitialized(upperBounds)) {
      upperBounds = new ArrayList<>(1);
    }
    getUpperBounds().add(minimize(bound));
    return this;
  }

  @Override
  public List<HxGenericElement> getLowerBounds() {
    return lowerBounds;
  }

  @Override
  public HxWildcardTypeImpl setLowerBounds(final List<HxGenericElement> lowerBounds) {
    this.lowerBounds = lowerBounds;
    return this;
  }

  public HxWildcardTypeImpl addLowerBound(HxGenericElement<?> bound) {
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

  @Override
  public String toString() {
    if(getLowerBounds().isEmpty() && getUpperBounds().size() == 1) {
      HxGenericElement hxGeneric = getUpperBounds().get(0);
      if(hxGeneric instanceof HxType &&
         "java.lang.Object".equals(((HxType) hxGeneric).getName())) {
        return "?";
      }
    }
    return "*";
  }
}
