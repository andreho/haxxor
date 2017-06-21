package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:10.
 */
public interface AspectStepType extends Comparable<AspectStepType> {

  /**
   * @return numeric order value of this step-type
   */
  int getOrder();

  /**
   * @param target
   * @return
   */
  boolean hasTarget(AspectStep.Target target);

  /**
   * @return
   */
  AspectStepParameterInjector getParameterInjector();

  /**
   * @return
   */
  AspectStepResultHandler getResultHandler();

  /**
   * @param def
   * @param type
   * @return
   */
  Collection<AspectStep<?>> buildSteps(AspectDefinition def, HxType type);

  @Override
  default int compareTo(AspectStepType o) {
    return Integer.compare(getOrder(), o.getOrder());
  }
}
