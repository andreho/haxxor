package net.andreho.aop.spi;

import net.andreho.aop.api.spec.Site;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Set;

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
  Site getSite();

  /**
   *
   * @return
   */
  boolean isSafely();

  /**
   *
   * @return
   */
  Set<String> getThrowableSet();

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
