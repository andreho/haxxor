package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:10.
 */
public interface AspectAdviceType
  extends Comparable<AspectAdviceType> {

  /**
   * @return numeric order value of this advice-type
   */
  int getOrder();

  /**
   * @param target
   * @return
   */
  boolean hasTarget(AspectAdvice.Target target);

  /**
   * @return
   */
  AspectAdviceParameterInjector getParameterInjector();

  /**
   * @return
   */
  AspectAdviceResultHandler getResultHandler();

  /**
   * @param def
   * @param type
   * @return
   */
  Collection<AspectAdvice<?>> buildAdvices(AspectDefinition def, HxType type);

  @Override
  default int compareTo(AspectAdviceType o) {
    return Integer.compare(getOrder(), o.getOrder());
  }
}