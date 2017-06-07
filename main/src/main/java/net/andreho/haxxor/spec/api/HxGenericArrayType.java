package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxGenericArrayType
    extends HxGeneric {

  /**
   * @return an instance of either
   * {@link net.andreho.haxxor.spec.api.HxType} or
   * {@link HxParameterizedType} or
   * {@link HxTypeVariable}
   */
  HxGeneric getGenericComponentType();

  HxGenericArrayType setGenericComponentType(final HxGeneric genericComponentType);
}
