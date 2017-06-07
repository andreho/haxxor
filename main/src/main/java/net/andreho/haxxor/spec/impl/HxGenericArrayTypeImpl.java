package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxGenericArrayType;

/**
 * <br/>Created by andreho on 3/26/16 at 10:24 PM.<br/>
 */
public class HxGenericArrayTypeImpl
    extends HxAbstractGeneric
    implements HxGenericArrayType {
  //-----------------------------------------------------------------------------------------------------------------

  private HxGeneric genericComponentType;

  //-----------------------------------------------------------------------------------------------------------------

  @Override
  public HxGeneric getGenericComponentType() {
    return genericComponentType;
  }

  @Override
  public HxGenericArrayTypeImpl setGenericComponentType(final HxGeneric genericComponentType) {
    this.genericComponentType = genericComponentType;
    return this;
  }

  @Override
  public HxGenericArrayType attach(final HxGeneric generic) {
    this.genericComponentType = generic;
    return this;
  }
}
