package net.andreho.aop.spi;

import net.andreho.aop.spec.Scope;
import net.andreho.aop.spec.Site;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:06.
 */
public interface AspectDefinition {

  /**
   * @return
   */
  String getClassname();


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
  Site getSite();

  /**
   * @return
   */
  EnumSet<Scope> getScope();

  /**
   * @return
   */
  TypeMatcher classes();

  /**
   * @return
   */
  TypeMatcherFactory getTypeMatcherFactory();

  /**
   * @return
   */
  List<AspectStep> getDefinedAspects();
}
