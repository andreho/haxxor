package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:10.
 */
public interface AspectStepType {

  /**
   * @param kind
   * @return
   */
  boolean hasKind(AspectStep.Kind kind);

  /**
   * @param def
   * @param type
   * @return
   */
  Collection<AspectStep<?>> buildSteps(AspectDefinition def, HxType type);
}
