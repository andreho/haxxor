package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 14:33.
 */
public interface HxInitializationAware {
  /**
   * @param type to initialize
   * @return the given type
   */
  HxType initialize(HxType type);
  /**
   * @param field to initialize
   * @return the given field
   */
  HxField initialize(HxField field);
  /**
   * @param method to initialize
   * @return the given method
   */
  HxMethod initialize(HxMethod method);
}
