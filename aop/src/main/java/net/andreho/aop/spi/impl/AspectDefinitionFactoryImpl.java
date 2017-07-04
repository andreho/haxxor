package net.andreho.aop.spi.impl;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectFactory;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOG = LoggerFactory.getLogger(AspectDefinitionImpl.class);
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
                                 final Map<String, AspectProfile> aspectProfiles,
                                 final Collection<AspectAdviceType> aspectAdviceTypes) {

    final Optional<HxAnnotation> aspectOptional = type.getAnnotation(ASPECT_ANNOTATION_TYPE);
    if (!aspectOptional.isPresent()) {
      return null;
    }

    final HxAnnotation aspectAnnotation = aspectOptional.get();
    final Map<String, List<String>> parameters = extractParameters(aspectAnnotation);
    final AspectFactory aspectFactory = createAspectFactoryIfAvailable(type);
    final String prefix = "__"; // aspectAnnotation.getAttribute("prefix", "__$");
    final String suffix = "__"; // aspectAnnotation.getAttribute("suffix", "$__");
    final String profileName = findAspectProfileName(aspectAnnotation);
    final ElementMatcher<HxType> classesMatcher =
      requireNonNull(aspectProfiles.get(profileName), "Profile not found: " + profileName)
      .getClassesMatcher();

    return new AspectDefinitionImpl(
      type,
      profileName,
      prefix,
      suffix,
      aspectAdviceTypes,
      classesMatcher,
      parameters,
      getElementMatcherFactory(),
      aspectProfiles,
      aspectFactory
    );
  }

  private AspectFactory createAspectFactoryIfAvailable(final HxType type) {
    final HxMethod aspectFactoryMethod = findAspectFactory(type);

    if(aspectFactoryMethod == null) {
      return null;
    }

    String attributeName =
      aspectFactoryMethod.getAnnotation(Attribute.class)
                         .map(annotation -> annotation.getAttribute("value", ""))
                         .orElse("");

    if(attributeName.isEmpty()) {
      attributeName = Constants.DEFAULT_ASPECT_ATTRIBUTE_NAME_PREFIX +
                      (aspectFactoryMethod.isConstructor()? aspectFactoryMethod.getDeclaringType() : aspectFactoryMethod.getReturnType())
      .getSimpleName();
    }

    return new AspectFactoryImpl(aspectFactoryMethod, attributeName, isAspectFactoryReusable(aspectFactoryMethod));
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

  protected String findAspectProfileName(HxAnnotation aspectAnnotation) {
    return aspectAnnotation.getAttribute("value", "");
  }

  protected HxMethod findAspectFactory(final HxType aspectType) {
    final Collection<HxMethod> constructorFactories =
      aspectType.constructors(
        (c) -> c.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));
    final Collection<HxMethod> methodFactories =
      aspectType.methods(
        (m) -> m.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));

    if (!constructorFactories.isEmpty()) {
      if (constructorFactories.size() != 1) {
        throw new IllegalStateException("There are more than one aspect-factory in: " + aspectType);
      }
      if (!methodFactories.isEmpty()) {
        throw new IllegalStateException("Multiple definitions of one aspect-factory are not allowed: " + aspectType);
      }
      final HxMethod constructor = constructorFactories.iterator().next();
      final HxType declaringType = constructor.getDeclaringType();

      if (!constructor.isPublic()) {
        throw new IllegalStateException("Defined constructor-factory isn't public: " + constructor);
      }
      if(!declaringType.isInstantiable() ||
         declaringType.isArray()) {
        throw new IllegalStateException(
          "Given constructor can't be used as an aspect-factory because the declaring type isn't instantiable: " +
          constructor);
      }
      return constructor;
    }

    if (!methodFactories.isEmpty()) {
      if (methodFactories.size() != 1) {
        throw new IllegalStateException("There are more than one aspect-factory in: " + aspectType);
      }
      final HxMethod method = methodFactories.iterator().next();
      if (!method.isPublic() || !method.isStatic()) {
        throw new IllegalStateException("Defined method-factory isn't public or static: " + method);
      }
      if(method.getReturnType().isVoid() || method.getReturnType().isArray()) {
        throw new IllegalStateException("Defined method-factory must return a valid aspect-instance: " + method);
      }
      return method;
    }
    return null;
  }

  protected boolean isAspectFactoryReusable(HxMethod aspectFactory) {
      return aspectFactory.getAnnotation(ASPECT_FACTORY_ANNOTATION_TYPE)
                          .map(annotation ->
                                 annotation.getAttribute("reuse", true))
                          .orElse(true);
  }
}
