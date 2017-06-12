package net.andreho.haxxor.spec.api;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxParameterizedType
    extends HxGenericElement<HxParameterizedType> {

  /**
   * @return
   */
  HxType getRawType();

  HxParameterizedType setRawType(HxType rawType);

  /**
   * @return
   */
  HxGenericElement<?> getOwnerType();

  HxParameterizedType setOwnerType(HxGenericElement<?> ownerType);

  /**
   * @return
   */
  List<HxGenericElement<?>> getActualTypeArguments();

  HxParameterizedType setActualTypeArguments(List<HxGenericElement<?>> generics);

  HxParameterizedType addActualTypeArgument(HxGenericElement<?> actualTypeArgument);
}
