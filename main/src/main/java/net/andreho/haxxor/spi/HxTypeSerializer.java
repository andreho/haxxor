package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:41.
 */
@FunctionalInterface
public interface HxTypeSerializer {
  /**
   * @param type to serialize
   * @return byte-code of the given type
   * @throws HxVerificationException
   */
  default byte[] serialize(HxType type) {
    return serialize(type, true);
  }

  /**
   * @param type to serialize
   * @param computeFrames
   * @return byte-code of the given type
   * @throws HxVerificationException
   */
  byte[] serialize(HxType type, boolean computeFrames);
}
