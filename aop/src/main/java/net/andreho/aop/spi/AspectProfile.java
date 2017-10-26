package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:27.
 */
public interface AspectProfile {

  /**
   *
   * @return
   */
  String getName();

  /**
   *
   * @return
   */
  ElementMatcher<HxParameter> getParametersMatcher();

  /**
   *
   * @return
   */
  ElementMatcher<HxMethod> getMethodsMatcher();

  /**
   *
   * @return
   */
  ElementMatcher<HxField> getFieldsMatcher();

  /**
   *
   * @return
   */
  ElementMatcher<HxType> getClassesMatcher();
}
