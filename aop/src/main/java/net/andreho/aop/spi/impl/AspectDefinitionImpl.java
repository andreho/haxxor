package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.andreho.aop.spi.impl.Constants.DISABLE_ANNOTATION_TYPE;
import static net.andreho.aop.spi.impl.Constants.EMPTY_TYPE_ARRAY;

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
  private final HxMethod aspectFactory;
  private final ElementMatcherFactory elementMatcherFactory;
  private final Map<AspectStep.Target, List<AspectStep<?>>> aspectStepsMap;
  private final List<AspectStep<?>> aspectSteps;
  private final Map<String, AspectProfile> aspectProfileMap;
  private final boolean aspectFactoryReusable;

  public AspectDefinitionImpl(final HxType type,
                              final String prefix,
                              final String suffix,
                              final Collection<AspectStepType> aspectStepTypes,
                              final Map<String, List<String>> parameters,
                              final ElementMatcher<HxType> classMatcher,
                              final ElementMatcherFactory elementMatcherFactory,
                              final Map<String, AspectProfile> aspectProfileMap,
                              final HxMethod aspectFactory,
                              final boolean aspectFactoryReusable) {
    this.type = type;
    this.prefix = prefix;
    this.suffix = suffix;
    this.aspectStepTypes = aspectStepTypes;
    this.classMatcher = classMatcher;
    this.parameters = parameters;
    this.aspectFactory = aspectFactory;
    this.aspectFactoryReusable = aspectFactoryReusable;
    this.elementMatcherFactory = elementMatcherFactory;
    this.aspectProfileMap = aspectProfileMap;

    this.aspectSteps = collectAspectSteps(type);
    this.aspectStepsMap = collectSupportedKinds(aspectSteps);
  }

  private Map<AspectStep.Target, List<AspectStep<?>>> collectSupportedKinds(final List<AspectStep<?>> aspectSteps) {
    final AspectStep.Target[] targets = AspectStep.Target.values();
    final Map<AspectStep.Target, List<AspectStep<?>>> mappedSteps =
      new EnumMap<>(AspectStep.Target.class);

    for(AspectStep step : aspectSteps) {
      for (AspectStep.Target target : targets) {
        if(step.hasTarget(target)) {
          mappedSteps.computeIfAbsent(target, (k) -> new ArrayList<>()).add(step);
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
  public String getName() {
    return type.getName();
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
  public String formTargetMethodName(final String originalMethodName) {
    return getPrefix() + originalMethodName + getSuffix();
  }

  @Override
  public Optional<HxMethod> getAspectFactory() {
    HxMethod aspectFactory = this.aspectFactory;
    return aspectFactory == null? Optional.empty() : Optional.of(aspectFactory);
  }

  @Override
  public boolean isFactoryReusable() {
    return aspectFactoryReusable;
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
  public Optional<AspectProfile> getAspectProfile(final String profileName) {
    return Optional.ofNullable(this.aspectProfileMap.get(profileName));
  }

  private List<AspectStep<?>> aspectStepsFor(AspectStep.Target target) {
    return aspectStepsMap.getOrDefault(target, Collections.emptyList());
  }

  @Override
  public boolean apply(final HxType type) {
    if(!getTypeMatcher().match(type)) {
      return false;
    }
    final AspectContext context = new AspectContextImpl(this);

    boolean typesModified = applyAspectsForTypes(type, context, aspectStepsFor(AspectStep.Target.TYPE));
    boolean fieldsModified = applyAspectsForFields(type, context, aspectStepsFor(AspectStep.Target.FIELD));
    boolean methodsModified = applyAspectsForMethods(type, context, aspectStepsFor(AspectStep.Target.METHOD));
    boolean constructorsModified = applyAspectsForConstructors(type, context, aspectStepsFor(AspectStep.Target.CONSTRUCTOR));

    return typesModified || fieldsModified || methodsModified || constructorsModified;
  }

  private boolean applyAspectsForConstructors(final HxType type,
                                              final AspectContext context,
                                              final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxMethod constructor : type.getConstructors().toArray(new HxMethod[0])) {
        if(isApplicable(constructor)) {
          context.enterConstructor(constructor);

          for(AspectStep step : aspectSteps) {
            if(step.apply(context, constructor)) {
              modified = true;
            }
          }
        }
      }
    }
    return modified;
  }

  private boolean isApplicable(final HxMethod method) {
    return !method.isSynthetic() &&
           isDisabled(method);
  }

  private boolean isApplicable(final HxField field) {
    return !field.isSynthetic() &&
           isDisabled(field);
  }

  private boolean isApplicable(final HxType type) {
    return isDisabled(type);
  }

  private boolean isDisabled(final HxAnnotated<?> annotated) {
    return !annotated.getAnnotation(DISABLE_ANNOTATION_TYPE)
          .filter(a -> Arrays.asList(a.getAttribute("value", EMPTY_TYPE_ARRAY)).contains(getType()))
          .isPresent();
  }

  private boolean applyAspectsForMethods(final HxType type,
                                         final AspectContext context,
                                         final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxMethod method : type.getMethods().toArray(new HxMethod[0])) {
        if(!method.isConstructor() && isApplicable(method)) {
          context.enterMethod(method);

          for(AspectStep step : aspectSteps) {
            if(step.apply(context, method)) {
              modified = true;
            }
          }
        }
      }
    }
    return modified;
  }

  private boolean applyAspectsForFields(final HxType type,
                                        final AspectContext context,
                                        final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      for(HxField field : type.getFields().toArray(new HxField[0])) {
        if(isApplicable(field)) {
          context.enterField(field);

          for(AspectStep step : aspectSteps) {
            if(step.apply(context, field)) {
              modified = true;
            }
          }
        }
      }
    }
    return modified;
  }

  private boolean applyAspectsForTypes(final HxType type,
                                       final AspectContext context,
                                       final List<AspectStep<?>> aspectSteps) {
    boolean modified = false;
    if(!aspectSteps.isEmpty()) {
      if(isApplicable(type)) {
        context.enterType(type);

        for(AspectStep step : aspectSteps) {
          if(step.apply(context, type)) {
            modified = true;
          }
        }
      }
    }
    return modified;
  }

  @Override
  public String toString() {
    return "@Aspect("+getName()+")";
  }
}
