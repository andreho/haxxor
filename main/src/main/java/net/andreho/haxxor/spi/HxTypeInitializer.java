package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxTypeInitializer extends HxInitializer<HxType> {

  /**
   * Initializes, prepares or adapts intern properties of the given type
   * @param type to modify according to user's needs
   */
  void initialize(HxType type);
}
