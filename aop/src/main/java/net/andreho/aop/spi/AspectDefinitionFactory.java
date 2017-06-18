package net.andreho.aop.spi;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:40.
 */
public interface AspectDefinitionFactory {

  /**
   * @param haxxor
   * @param type
   * @return
   */
  AspectDefinition create(Haxxor haxxor, HxType type);
}
