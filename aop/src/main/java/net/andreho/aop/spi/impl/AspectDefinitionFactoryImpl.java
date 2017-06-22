package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 18.06.2017 at 02:41.
 */
public class AspectDefinitionFactoryImpl
  implements AspectDefinitionFactory,
             Constants {

  private final ElementMatcherFactory elementMatcherFactory;

  public AspectDefinitionFactoryImpl(final ElementMatcherFactory elementMatcherFactory) {
    this.elementMatcherFactory = requireNonNull(elementMatcherFactory);
  }

  public ElementMatcherFactory getElementMatcherFactory() {
    return elementMatcherFactory;
  }

  @Override
  public AspectDefinition create(final Haxxor haxxor,
                                 final HxType type,
                                 final Collection<AspectProfile> aspectProfiles,
                                 final Collection<AspectStepType> aspectStepTypes) {

    final Optional<HxAnnotation> aspectOptional = type.getAnnotation(ASPECT_ANNOTATION_TYPE);
    if (!aspectOptional.isPresent()) {
      return null;
    }

    final HxAnnotation aspectAnnotation = aspectOptional.get();
    final Map<String, List<String>> parameters = extractParameters(aspectAnnotation);
    final HxMethod aspectFactory = findAspectFactory(type);
    final String prefix = "__"; // aspectAnnotation.getAttribute("prefix", "__$");
    final String suffix = "__"; // aspectAnnotation.getAttribute("suffix", "$__");

    return new AspectDefinitionImpl(
      type,
      prefix,
      suffix,
      aspectStepTypes,
      parameters,
      getElementMatcherFactory().createClassesFilter(type),
      getElementMatcherFactory(),
      buildNamedAspectProfileMap(aspectProfiles),
      aspectFactory,
      isAspectFactoryReusable(aspectFactory)
    );
  }

  private Map<String, AspectProfile> buildNamedAspectProfileMap(final Collection<AspectProfile> aspectProfiles) {
    return aspectProfiles.stream().collect(Collectors.toMap(AspectProfile::getName, Function.identity()));
  }

  protected Map<String, List<String>> extractParameters(HxAnnotation aspectAnnotation) {
    final Map<String, List<String>> parameters = new LinkedHashMap<>();
    final HxAnnotation[] parametersArray = aspectAnnotation.getAttribute("parameters", EMPTY_ANNOTATION_ARRAY);

    for (HxAnnotation parameterAnnotation : parametersArray) {
      String name = parameterAnnotation.getAttribute("name");
      String[] value = parameterAnnotation.getAttribute("value", EMPTY_STRING_ARRAY);
      parameters.computeIfAbsent(name, (key) -> new ArrayList<>()).addAll(Arrays.asList(value));
    }

    return parameters.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(parameters);
  }

  protected HxMethod findAspectFactory(final HxType aspectType) {
    final Collection<HxMethod> constructorFactories =
      aspectType.constructors(
        (c) -> c.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));
    final Collection<HxMethod> methodFactories =
      aspectType.methods(
        (m) -> m.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));

    if (!constructorFactories.isEmpty()) {
      if (constructorFactories.size() != 1 || !methodFactories.isEmpty()) {
        throw new IllegalStateException("There are more than one aspect factory: " + aspectType);
      }
      HxMethod constructor = constructorFactories.iterator().next();
      if (!constructor.isPublic()) {
        throw new IllegalStateException("Defined constructor-factory isn't public: " + constructor);
      }
      return constructor;
    }

    if (!methodFactories.isEmpty()) {
      if (methodFactories.size() != 1) {
        throw new IllegalStateException("There are more than one aspect factory: " + aspectType);
      }
      HxMethod method = methodFactories.iterator().next();
      if (!method.isPublic() || !method.isStatic()) {
        throw new IllegalStateException("Defined method-factory isn't public or static: " + method);
      }
      return method;
    }
    return null;
  }

  protected boolean isAspectFactoryReusable(HxMethod aspectFactory) {
    if (aspectFactory != null) {
      return aspectFactory.getAnnotation(ASPECT_FACTORY_ANNOTATION_TYPE)
                          .map(annotation ->
                                 annotation.getAttribute("reuse", true))
                          .orElse(true);
    }
    return false;
  }
}
