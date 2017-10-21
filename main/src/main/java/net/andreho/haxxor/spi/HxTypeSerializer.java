package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:41.
 */
@FunctionalInterface
public interface HxTypeSerializer {
  /**
   * @param type
   * @return
   */
  default byte[] serialize(HxType type) {
    return serialize(type, true);
  }

  /**
   * @param type
   * @param computeFrames
   * @return
   */
  byte[] serialize(HxType type, boolean computeFrames);
}
