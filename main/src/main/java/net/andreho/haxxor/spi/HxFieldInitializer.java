package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxFieldInitializer extends HxInitializer<HxField> {

  /**
   * Initializes, prepares or adapts intern properties of the given field
   * @param field to modify according to user's needs
   */
  void initialize(HxField field);
}
