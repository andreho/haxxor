package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxParameterizedType;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by andreho on 3/26/16 at 10:24 PM.<br/>
 */
public class HxParameterizedTypeImpl
    extends HxAbstractGeneric<HxParameterizedType>
    implements HxParameterizedType {

  private HxType rawType;
  private HxGeneric ownerType;
  private List<HxGeneric<?>> actualTypeArguments = Collections.emptyList();

  @Override
  public HxType getRawType() {
    return rawType;
  }

  @Override
  public HxParameterizedTypeImpl setRawType(HxType rawType) {
    this.rawType = rawType;
    return this;
  }

  @Override
  public HxGeneric<?> getOwnerType() {
    return ownerType;
  }

  @Override
  public HxParameterizedTypeImpl setOwnerType(HxGeneric<?> ownerType) {
    this.ownerType = ownerType;
    return this;
  }

  @Override
  public List<HxGeneric<?>> getActualTypeArguments() {
    return actualTypeArguments;
  }

  @Override
  public HxParameterizedTypeImpl setActualTypeArguments(List<HxGeneric<?>> actualTypeArguments) {
    this.actualTypeArguments = actualTypeArguments;
    return this;
  }

  @Override
  public HxParameterizedTypeImpl addActualTypeArgument(final HxGeneric<?> actualTypeArgument) {
    if (isUninitialized(actualTypeArguments)) {
      setActualTypeArguments(new ArrayList<>());
    }
    getActualTypeArguments().add(actualTypeArgument);
    return this;
  }

  @Override
  public HxGeneric<?> minimize() {
    if(getActualTypeArguments().isEmpty()) {
      return getRawType();
    }
    return this;
  }

//  @Override
//  public HxParameterizedTypeImpl attach(final HxGeneric generic) {
//    if(Utils.isUninitialized(actualTypeArguments)) {
//      setActualTypeArguments(new ArrayList<>());
//    }
//    getActualTypeArguments().add(generic);
//    return this;
//  }
}
