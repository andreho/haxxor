package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxModifiers;
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
  private final Collection<AspectAdviceType> aspectAdviceTypes;
  private final ElementMatcher<HxType> classMatcher;
  private final Map<String, List<String>> parameters;
  private final HxMethod aspectFactory;
  private final ElementMatcherFactory elementMatcherFactory;
  private final Map<AspectAdvice.Target, List<AspectAdvice<?>>> aspectStepsMap;
  private final List<AspectAdvice<?>> aspectAdvices;
  private final Map<String, AspectProfile> aspectProfileMap;
  private final boolean aspectFactoryReusable;

  public AspectDefinitionImpl(final HxType type,
                              final String prefix,
                              final String suffix,
                              final Collection<AspectAdviceType> aspectAdviceTypes,
                              final Map<String, List<String>> parameters,
                              final ElementMatcher<HxType> classMatcher,
                              final ElementMatcherFactory elementMatcherFactory,
                              final Map<String, AspectProfile> aspectProfileMap,
                              final HxMethod aspectFactory,
                              final boolean aspectFactoryReusable) {
    this.type = type;
    this.prefix = prefix;
    this.suffix = suffix;
    this.aspectAdviceTypes = aspectAdviceTypes;
    this.classMatcher = classMatcher;
    this.parameters = parameters;
    this.aspectFactory = aspectFactory;
    this.aspectFactoryReusable = aspectFactoryReusable;
    this.elementMatcherFactory = elementMatcherFactory;
    this.aspectProfileMap = aspectProfileMap;

    this.aspectAdvices = collectAspectSteps(type);
    this.aspectStepsMap = collectSupportedKinds(aspectAdvices);
  }

  private Map<AspectAdvice.Target, List<AspectAdvice<?>>> collectSupportedKinds(final List<AspectAdvice<?>> aspectAdvices) {
    final AspectAdvice.Target[] targets = AspectAdvice.Target.values();
    final Map<AspectAdvice.Target, List<AspectAdvice<?>>> mappedSteps =
      new EnumMap<>(AspectAdvice.Target.class);

    for(AspectAdvice step : aspectAdvices) {
      for (AspectAdvice.Target target : targets) {
        if(step.hasTarget(target)) {
          mappedSteps.computeIfAbsent(target, (k) -> new ArrayList<>()).add(step);
        }
      }
    }
    return mappedSteps;
  }

  protected List<AspectAdvice<?>> collectAspectSteps(final HxType type) {
    final List<AspectAdvice<?>> allSteps = new ArrayList<>();

    for(AspectAdviceType stepType : getAspectAdviceTypes()) {
      Collection<AspectAdvice<?>> foundSteps = stepType.buildAdvices(this, type);
      allSteps.addAll(foundSteps);
    }

    Collections.sort(allSteps);

    for (AspectAdvice<?> step : allSteps) {
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
  public Collection<AspectAdviceType> getAspectAdviceTypes() {
    return aspectAdviceTypes;
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
  public List<AspectAdvice<?>> getAspectAdvices() {
    return aspectAdvices;
  }

  @Override
  public Optional<AspectProfile> getAspectProfile(final String profileName) {
    return Optional.ofNullable(this.aspectProfileMap.get(profileName));
  }

  private List<AspectAdvice<?>> aspectStepsFor(AspectAdvice.Target target) {
    return aspectStepsMap.getOrDefault(target, Collections.emptyList());
  }

  @Override
  public boolean apply(final HxType type) {
    if(!getTypeMatcher().match(type)) {
      return false;
    }
    final AspectContext context = new AspectContextImpl(this);

    boolean typesModified = applyAspectsForTypes(type, context, aspectStepsFor(AspectAdvice.Target.TYPE));
    boolean fieldsModified = applyAspectsForFields(type, context, aspectStepsFor(AspectAdvice.Target.FIELD));
    boolean methodsModified = applyAspectsForMethods(type, context, aspectStepsFor(AspectAdvice.Target.METHOD));
    boolean constructorsModified = applyAspectsForConstructors(type, context, aspectStepsFor(AspectAdvice.Target.CONSTRUCTOR));

    return typesModified || fieldsModified || methodsModified || constructorsModified;
  }

  private boolean applyAspectsForConstructors(final HxType type,
                                              final AspectContext context,
                                              final List<AspectAdvice<?>> aspectAdvices) {
    boolean modified = false;
    if(!aspectAdvices.isEmpty()) {
      for(HxMethod constructor : type.getConstructors().toArray(new HxMethod[0])) {
        if(isApplicable(constructor)) {
          context.enterConstructor(constructor);

          for(AspectAdvice step : aspectAdvices) {
            if(step.apply(context, constructor)) {
              modified = true;
            }
          }
        }
      }
    }
    return modified;
  }

  private boolean isSyntheticAllowed(final HxMember<?> member) {
    return !member.hasModifiers(HxModifiers.SYNTHETIC);
  }

  private boolean isApplicable(final HxMethod method) {
    return isSyntheticAllowed(method) &&
           isDisabled(method);
  }

  private boolean isApplicable(final HxField field) {
    return isSyntheticAllowed(field) &&
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
                                         final List<AspectAdvice<?>> aspectAdvices) {
    boolean modified = false;
    if(!aspectAdvices.isEmpty()) {
      for(HxMethod method : type.getMethods().toArray(new HxMethod[0])) {
        if(!method.isConstructor() && isApplicable(method)) {
          context.enterMethod(method);

          for(AspectAdvice step : aspectAdvices) {
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
                                        final List<AspectAdvice<?>> aspectAdvices) {
    boolean modified = false;
    if(!aspectAdvices.isEmpty()) {
      for(HxField field : type.getFields().toArray(new HxField[0])) {
        if(isApplicable(field)) {
          context.enterField(field);

          for(AspectAdvice step : aspectAdvices) {
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
                                       final List<AspectAdvice<?>> aspectAdvices) {
    boolean modified = false;
    if(!aspectAdvices.isEmpty()) {
      if(isApplicable(type)) {
        context.enterType(type);

        for(AspectAdvice step : aspectAdvices) {
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
