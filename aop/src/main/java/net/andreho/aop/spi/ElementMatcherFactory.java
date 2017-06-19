package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
public interface ElementMatcherFactory {

  /**
   * @param aspectType
   * @return
   */
  ElementMatcher<HxType> create(HxType aspectType);

  /**
   * @param aspectMethod
   * @param methodsAnnotations
   * @return
   */
  ElementMatcher<HxMethod> create(HxMethod aspectMethod, HxAnnotation[] methodsAnnotations);

  /**
   * @param aspectConstructor
   * @param methodsAnnotations
   * @return
   */
  ElementMatcher<HxConstructor> create(HxConstructor aspectConstructor, HxAnnotation[] methodsAnnotations);
}
