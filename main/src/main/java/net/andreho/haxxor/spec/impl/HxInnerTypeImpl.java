package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxInnerType;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 08:30.
 */
public class HxInnerTypeImpl extends HxMemberImpl<HxInnerType> implements HxInnerType {

  private final Haxxor haxxor;
  private final String name;

  public HxInnerTypeImpl(final Haxxor haxxor,
                         final String name) {
    this.haxxor = haxxor;
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  public boolean isLocalType() {
    return false;
  }

  @Override
  public boolean isMemberType() {
    return false;
  }

  @Override
  public HxType toType() {
    return getHaxxor().resolve(getName());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final HxInnerTypeImpl that = (HxInnerTypeImpl) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return getName();
  }
}
