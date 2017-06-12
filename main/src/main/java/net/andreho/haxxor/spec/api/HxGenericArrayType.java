package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxGenericArrayType
    extends HxGenericElement<HxGenericArrayType> {

  /**
   * @return an instance of either
   * {@link net.andreho.haxxor.spec.api.HxType} or
   * {@link HxParameterizedType} or
   * {@link HxTypeVariable}
   */
  HxGenericElement<?> getGenericComponentType();

  HxGenericArrayType setGenericComponentType(final HxGenericElement<?> genericComponentType);
}
