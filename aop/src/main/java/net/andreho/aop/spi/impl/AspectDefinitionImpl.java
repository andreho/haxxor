package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectApplicationContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
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
  private final Collection<AspectStepType> aspectStepTypes;
  private final ElementMatcher<HxType> classMatcher;
  private final Map<String, List<String>> parameters;
  private final Optional<HxExecutable<?>> aspectFactory;
  private final ElementMatcherFactory elementMatcherFactory;
  private final List<AspectStep<?>> aspectSteps;
  private final Map<AspectStep.Kind, List<AspectStep<?>>> aspectStepsMap;

  public AspectDefinitionImpl(final HxType type,
                              final String prefix,
                              final String suffix,
                              final Collection<AspectStepType> aspectStepTypes,
                              final Map<String, List<String>> parameters,
                              final ElementMatcher<HxType> classMatcher,
                              final Optional<HxExecutable<?>> aspectFactory,
                              final ElementMatcherFactory elementMatcherFactory) {
    this.type = type;
    this.prefix = prefix;
    this.suffix = suffix;
    this.aspectStepTypes = aspectStepTypes;
    this.classMatcher = classMatcher;
    this.parameters = parameters;
    this.aspectFactory = aspectFactory;
    this.elementMatcherFactory = elementMatcherFactory;

    this.aspectSteps = collectAspectSteps(type);
    this.aspectStepsMap = collectSupportedKinds(aspectSteps);
  }

  private Map<AspectStep.Kind, List<AspectStep<?>>> collectSupportedKinds(final List<AspectStep<?>> aspectSteps) {
    final AspectStep.Kind[] kinds = AspectStep.Kind.values();
    final Map<AspectStep.Kind, List<AspectStep<?>>> mappedSteps =
      new EnumMap<>(AspectStep.Kind.class);

    for(AspectStep step : aspectSteps) {
      for (AspectStep.Kind kind : kinds) {
        if(step.hasKind(kind)) {
          mappedSteps.computeIfAbsent(kind, (k) -> new ArrayList<>()).add(step);
        }
      }
    }
    return mappedSteps;
  }

  protected List<AspectStep<?>> collectAspectSteps(final HxType type) {
    final List<AspectStep<?>> allSteps = new ArrayList<>();

    for(AspectStepType stepType : getAspectStepTypes()) {
      Collection<AspectStep<?>> foundSteps = stepType.buildSteps(this, type);
      allSteps.addAll(foundSteps);
    }

    Collections.sort(allSteps);

    for (AspectStep<?> step : allSteps) {
      if(step.needsAspectFactory() && !getAspectFactory().isPresent()) {
        throw new IllegalStateException(
          "Given aspect's step can only be used if an aspect-factory is provided via @Aspect.Factory annotation: "+step.getInterceptor());
      }
    }

    return allSteps;
  }

  @Override
  public HxType getType() {
    return type;
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
  public Optional<HxExecutable<?>> getAspectFactory() {
    return aspectFactory;
  }

  @Override
  public Map<String, List<String>> getParameters() {
    return parameters;
  }

  @Override
  public Collection<AspectStepType> getAspectStepTypes() {
    return aspectStepTypes;
  }

  @Override
  public ElementMatcher<HxType> getTypeMatcher() {
    return classMatcher;
  }

  @Override
  public ElementMatcherFactory getElementMatcherFactory() {
    return elementMatcherFactory;
  }

  @Override
  public List<AspectStep<?>> getAspectSteps() {
    return aspectSteps;
  }

  @Override
  public Optional<AspectStep<?>> getProfiledAspectStep(final String profileName) {
    return Optional.empty();
  }

  private List<AspectStep<?>> aspectStepsFor(AspectStep.Kind kind) {
    return aspectStepsMap.getOrDefault(kind, Collections.emptyList());
  }

  @Override
  public boolean apply(final HxType type) {
    if(!getTypeMatcher().match(type)) {
      return false;
    }
    final AspectApplicationContext context = new AspectApplicationContextImpl();

    boolean typesModified = applyAspectsForTypes(type, context, aspectStepsFor(AspectStep.Kind.TYPE));
    boolean fieldsModified = applyAspectsForFields(type, context, aspectStepsFor(AspectStep.Kind.FIELD));
    boolean methodsModified = applyAspectsForMethods(type, context, aspectStepsFor(AspectStep.Kind.METHOD));
    boolean constructorsModified = applyAspectsForConstructors(type, context, aspectStepsFor(AspectStep.Kind.CONSTRUCTOR));

    return typesModified || fieldsModified || methodsModified || constructorsModified;
  }

  private boolean applyAspectsForConstructors(final HxType type,
                                              final AspectApplicationContext context,
                                              final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxConstructor constructor : type.getConstructors()) {
        for(AspectStep step : aspectSteps) {
          if(step.apply(this, context, constructor)) {
            modified = true;
          }
        }
      }
    }
    return modified;
  }

  private boolean applyAspectsForMethods(final HxType type,
                                         final AspectApplicationContext context,
                                         final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxMethod method : type.getMethods()) {
        for(AspectStep step : aspectSteps) {
          if(step.apply(this, context, method)) {
            modified = true;
          }
        }
      }
    }
    return modified;
  }

  private boolean applyAspectsForFields(final HxType type,
                                        final AspectApplicationContext context,
                                        final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxField field : type.getFields()) {
        for(AspectStep step : aspectSteps) {
          if(step.apply(this, context, field)) {
            modified = true;
          }
        }
      }
    }
    return modified;
  }

  private boolean applyAspectsForTypes(final HxType type,
                                       final AspectApplicationContext context,
                                       final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(AspectStep step : aspectSteps) {
        if(step.apply(this, context, type)) {
          modified = true;
        }
      }
    }
    return modified;
  }
}
