package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
public interface AspectTypeMatcherFactory {

  /**
   * @param aspectType
   * @return
   */
  AspectMatcher<HxType> create(HxType aspectType);

  /**
   * @param aspectMethod
   * @param aspectAnnotation
   * @return
   */
  AspectMatcher<HxMethod> create(HxMethod aspectMethod, HxAnnotation aspectAnnotation);

  /**
   * @param aspectConstructor
   * @param aspectAnnotation
   * @return
   */
  AspectMatcher<HxConstructor> create(HxConstructor aspectConstructor, HxAnnotation aspectAnnotation);
}
