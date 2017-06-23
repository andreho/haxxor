package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectDefinition {

  /**
   * @return
   */
  String getName();

  /**
   * @return
   */
  HxType getType();

  /**
   * @return
   */
  Map<String, List<String>> getParameters();

  /**
   * @return
   */
  Optional<HxMethod> getAspectFactory();

  /**
   * @return
   */
  boolean isFactoryReusable();

  /**
   * @return
   */
  Collection<AspectAdviceType> getAspectAdviceTypes();

  /**
   * @param originalMethodName
   * @return
   */
  String formTargetMethodName(String originalMethodName);

  /**
   * @return
   */
  String getPrefix();

  /**
   * @return
   */
  String getSuffix();

  /**
   * @return
   */
  ElementMatcher<HxType> getTypeMatcher();

  /**
   * @return
   */
  ElementMatcherFactory getElementMatcherFactory();

  /**
   * @return
   */
  List<AspectAdvice<?>> getAspectAdvices();

  /**
   * @param profileName
   * @return
   */
  Optional<AspectProfile> getAspectProfile(String profileName);

  /**
   * @param type
   * @return
   */
  boolean apply(HxType type);
}
