package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectMatcher;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectTypeMatcherFactory;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:07.
 */
public class AspectDefinitionImpl implements AspectDefinition {

  private final HxType type;
  private final String prefix;
  private final String suffix;
  private final AspectMatcher classMatcher;
  private final Map<String, List<String>> parameters;
  private final Optional<HxExecutable<?>> aspectFactory;
  private final AspectTypeMatcherFactory aspectTypeMatcherFactory;
  private final List<AspectStep> definedAspects;

  public AspectDefinitionImpl(final HxType type,
                              final String prefix,
                              final String suffix,
                              final Map<String, List<String>> parameters,
                              final AspectMatcher classMatcher,
                              final Optional<HxExecutable<?>> aspectFactory,
                              final AspectTypeMatcherFactory aspectTypeMatcherFactory,
                              final List<AspectStep> definedAspects) {
    this.type = type;
    this.prefix = prefix;
    this.suffix = suffix;
    this.classMatcher = classMatcher;
    this.parameters = parameters;
    this.aspectFactory = aspectFactory;
    this.definedAspects = definedAspects;
    this.aspectTypeMatcherFactory = aspectTypeMatcherFactory;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public Optional<HxExecutable<?>> getAspectFactory() {
    return aspectFactory;
  }

  @Override
  public Map<String, List<String>> getParameters() {
    return parameters;
  }

  @Override
  public String getPrefix() {
    return prefix;
  }

  @Override
  public String getSuffix() {
    return suffix;
  }

  @Override
  public AspectMatcher getClassMatcher() {
    return classMatcher;
  }

  @Override
  public AspectTypeMatcherFactory getTypeMatcherFactory() {
    return aspectTypeMatcherFactory;
  }

  @Override
  public List<AspectStep> getDefinedAspects() {
    return definedAspects;
  }

  @Override
  public boolean apply(final HxType type) {
    return false;
  }
}
