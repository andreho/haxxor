package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxGenericArrayType;
import net.andreho.haxxor.api.HxGenericElement;

/**
 * <br/>Created by andreho on 3/26/16 at 10:24 PM.<br/>
 */
public class HxGenericArrayTypeImpl
    extends HxAbstractGeneric<HxGenericArrayType>
    implements HxGenericArrayType {
  private HxGenericElement<?> genericComponentType;

  @Override
  public HxGenericElement<?> getGenericComponentType() {
    return genericComponentType;
  }

  public HxGenericArrayTypeImpl setGenericComponentType(final HxGenericElement<?> genericComponentType) {
    this.genericComponentType = genericComponentType;
    return this;
  }

  @Override
  public String toString() {
    return getGenericComponentType()+"[]";
  }
}
