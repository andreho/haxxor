package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxMethodInitializer extends HxInitializer<HxMethod> {

  /**
   * Initializes, prepares or adapts intern properties of the given method
   * @param method to modify according to user's needs
   */
  void initialize(HxMethod method);
}
