package net.andreho.aop.spi;

import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectDefinition extends Comparable<AspectDefinition> {

  /**
   * @return of this aspect
   * @see net.andreho.aop.api.Order
   * @see net.andreho.aop.api.Aspect
   */
  int getOrder();

  /**
   * @return name of this aspect - usually it's a simple classname
   */
  String getName();

  /**
   * @return the name of used selection profile
   */
  String getProfile();

  /**
   * @return the represented aspect type itself
   */
  HxType getType();

  /**
   * @return
   */
  List<AspectFactory> getAspectFactories();

  /**
   * @param type
   * @return
   */
  Optional<AspectFactory> findAspectFactoryFor(HxType type);

  /**
   * @return
   */
  Map<String, List<String>> getParameters();

  /**
   * @return
   */
  Collection<AspectAdviceType> getAdviceTypes();

  /**
   * @param originalMethodName
   * @return
   */
  String createShadowMethodName(String originalMethodName);

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
  List<AspectAdvice<?>> getAdvices();

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
