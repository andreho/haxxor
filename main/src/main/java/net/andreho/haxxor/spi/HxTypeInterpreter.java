package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 13.06.2017 at 20:41.
 */
@FunctionalInterface
public interface HxTypeInterpreter {
  /**
   * @param type
   * @return
   */
  default byte[] interpret(HxType type) {
    return interpret(type, true);
  }

  /**
   * @param type
   * @return
   */
  byte[] interpret(HxType type, boolean computeFrames);
}
