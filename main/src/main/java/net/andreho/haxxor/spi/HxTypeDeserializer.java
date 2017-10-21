package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:41.
 */
@FunctionalInterface
public interface HxTypeDeserializer {
  /**
   * @param classname
   * @param byteCode
   * @return
   */
  default HxType deserialize(final String classname, byte[] byteCode) {
    return deserialize(classname, byteCode, 0);
  }

  /**
   * @param classname
   * @param byteCode
   * @param flags
   * @return
   */
  HxType deserialize(final String classname, byte[] byteCode, int flags);
}
