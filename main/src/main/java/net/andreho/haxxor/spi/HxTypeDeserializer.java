package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:41.
 */
@FunctionalInterface
public interface HxTypeDeserializer {

  /**
   * @param type
   * @param byteCode
   * @return
   */
  default HxType deserialize(final HxType type, byte[] byteCode) {
    return deserialize(type, byteCode, 0);
  }

  /**
   * @param type
   * @param byteCode
   * @param flags
   * @return
   */
  HxType deserialize(final HxType type, byte[] byteCode, int flags);
}
