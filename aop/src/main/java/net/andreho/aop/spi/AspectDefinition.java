package net.andreho.aop.spi;

import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxType;

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
  HxType getType();

  /**
   * @return
   */
  Optional<HxExecutable<?>> getAspectFactory();

  /**
   * @return
   */
  Map<String, List<String>> getParameters();

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
  AspectMatcher getClassMatcher();

  /**
   * @return
   */
  AspectTypeMatcherFactory getTypeMatcherFactory();

  /**
   * @return
   */
  List<AspectStep> getDefinedAspects();

  /**
   * @param type
   * @return
   */
  boolean apply(HxType type);
}
