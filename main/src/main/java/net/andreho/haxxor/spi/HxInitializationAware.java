package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 26.10.2017 at 14:33.
 */
public interface HxInitializationAware {
  HxType initialize(HxType type);

  HxField initialize(HxField field);

  HxMethod initialize(HxMethod method);
}
