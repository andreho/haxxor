package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxGenericElement;
import net.andreho.haxxor.spec.api.HxParameterizedType;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by andreho on 3/26/16 at 10:24 PM.<br/>
 */
public class HxParameterizedTypeImpl
    extends HxAbstractGeneric<HxParameterizedType>
    implements HxParameterizedType {

  private HxType rawType;
  private HxGenericElement ownerType;
  private List<HxGenericElement<?>> actualTypeArguments = Collections.emptyList();

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
  public HxGenericElement<?> getOwnerType() {
    return ownerType;
  }

  @Override
  public HxParameterizedTypeImpl setOwnerType(HxGenericElement<?> ownerType) {
    this.ownerType = ownerType;
    return this;
  }

  @Override
  public List<HxGenericElement<?>> getActualTypeArguments() {
    return actualTypeArguments;
  }

  @Override
  public HxParameterizedTypeImpl setActualTypeArguments(List<HxGenericElement<?>> actualTypeArguments) {
    this.actualTypeArguments = actualTypeArguments;
    return this;
  }

  @Override
  public HxParameterizedTypeImpl addActualTypeArgument(final HxGenericElement<?> actualTypeArgument) {
    if (isUninitialized(actualTypeArguments)) {
      setActualTypeArguments(new ArrayList<>(1));
    }
    getActualTypeArguments().add(actualTypeArgument);
    return this;
  }

  @Override
  public HxGenericElement<?> minimize() {
    if(getActualTypeArguments().isEmpty()) {
      return getRawType();
    }
    return this;
  }

  @Override
  public String toString() {
    HxType component = getRawType();
//    int dim = 0;
//    while(component.isArray()) {
//      component = component.getComponentType().get();
//      dim++;
//    }

    StringBuilder builder = new StringBuilder().append(component);
    Iterator<HxGenericElement<?>> iterator = getActualTypeArguments().iterator();
    if(iterator.hasNext()) {
      builder.append('<');
      HxGenericElement<?> hxGeneric = iterator.next();
      if(hxGeneric instanceof HxTypeVariable) {
        builder.append(((HxTypeVariable) hxGeneric).getName());
      } else {
        builder.append(hxGeneric);
      }
      while(iterator.hasNext()) {
        hxGeneric = iterator.next();
        builder.append(',');
        if(hxGeneric instanceof HxTypeVariable) {
          builder.append(((HxTypeVariable) hxGeneric).getName());
        } else {
          builder.append(hxGeneric);
        }
      }
      builder.append('>');
    }
//    while(dim-- > 0) {
//      builder.append("[]");
//    }
    return builder.toString();
  }
}
