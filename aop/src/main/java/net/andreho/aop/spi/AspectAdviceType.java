package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Annotation;
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
  ParameterInjectorSelector getParameterInjectorSelector();

  /**
   * @return
   */
  ResultPostProcessor getResultPostProcessor();

  /**
   * @param activatorAnnotation
   * @return
   */
  boolean isActivatedThrough(Class<? extends Annotation> activatorAnnotation);

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
