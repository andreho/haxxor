package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 03:14.
 */
public interface ElementMatcherFactory {

  /**
   * @param aspectType
   * @return
   */
  ElementMatcher<HxType> createClassesFilter(HxType aspectType);

  /**
   * @param classesAnnotations
   * @return
   */
  ElementMatcher<HxType> createClassesFilter(HxAnnotation[] classesAnnotations);

  /**
   * @param methodsAnnotations
   * @return
   */
  ElementMatcher<HxMethod> createMethodsFilter(HxAnnotation[] methodsAnnotations);

  /**
   * @param fieldsAnnotations
   * @return
   */
  ElementMatcher<HxField> createFieldsFilter(HxAnnotation[] fieldsAnnotations);

  /**
   * @param parametersAnnotations
   * @return
   */
  ElementMatcher<HxParameter> createParametersFilter(HxAnnotation[] parametersAnnotations);
}
