package net.andreho.haxxor.spec.api;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 11.03.2016.<br/>
 */
public interface HxParameterizedType
    extends HxGeneric<HxParameterizedType> {

  /**
   * @return
   */
  HxType getRawType();

  HxParameterizedType setRawType(HxType rawType);

  /**
   * @return
   */
  HxGeneric<?> getOwnerType();

  HxParameterizedType setOwnerType(HxGeneric<?> ownerType);

  /**
   * @return
   */
  List<HxGeneric<?>> getActualTypeArguments();

  HxParameterizedType setActualTypeArguments(List<HxGeneric<?>> generics);

  HxParameterizedType addActualTypeArgument(HxGeneric<?> actualTypeArgument);
}
