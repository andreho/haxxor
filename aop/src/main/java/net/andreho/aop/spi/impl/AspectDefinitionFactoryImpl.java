package net.andreho.aop.spi.impl;

import net.andreho.aop.api.Order;
import net.andreho.aop.api.StandardOrder;
import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectDefinitionFactory;
import net.andreho.aop.spi.AspectFactory;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
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
  public AspectDefinition create(final Hx haxxor,
                                 final HxType type,
                                 final Map<String, AspectProfile> aspectProfiles,
                                 final Collection<AspectAdviceType> adviceTypes) {

    final Optional<HxAnnotation> aspectOptional = type.getAnnotation(ASPECT_ANNOTATION_TYPE);
    if (!aspectOptional.isPresent()) {
      return null;
    }

    final int order = extractAspectOrder(type);
    final HxAnnotation aspectAnnotation = aspectOptional.get();
    final Map<String, List<String>> parameters = extractParameters(aspectAnnotation);
    final List<AspectFactory> aspectFactories = createAspectFactoriesIfAvailable(type);
    final String prefix = "__"; // aspectAnnotation.getAttribute("prefix", "__");
    final String suffix = "__"; // aspectAnnotation.getAttribute("suffix", "__");
    final String profileName = findAspectProfileName(aspectAnnotation);
    final ElementMatcher<HxType> classesMatcher =
      requireNonNull(aspectProfiles.get(profileName), "Profile not found: " + profileName)
      .getClassesMatcher();

    return new AspectDefinitionImpl(
      order,
      type,
      profileName,
      prefix,
      suffix,
      adviceTypes,
      classesMatcher,
      parameters,
      getElementMatcherFactory(),
      aspectProfiles,
      aspectFactories
    );
  }

  private List<AspectFactory> createAspectFactoriesIfAvailable(final HxType type) {
    System.err.println("TODO: List<AspectFactory> createAspectFactoriesIfAvailable(final HxType type)");

    final List<HxMethod> aspectFactoryMethods = findAspectFactories(type);

    if(aspectFactoryMethods == null || aspectFactoryMethods.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectFactory> aspectFactories = new ArrayList<>(aspectFactoryMethods.size());

    for(HxMethod aspectFactoryMethod : aspectFactoryMethods) {
      String attributeName =
        aspectFactoryMethod.getAnnotation(Attribute.class)
                           .map(annotation -> annotation.getAttribute("value", ""))
                           .orElse("");
      if(attributeName.isEmpty()) {
        attributeName = Constants.DEFAULT_ASPECT_ATTRIBUTE_NAME_PREFIX +
                        (aspectFactoryMethod.isConstructor()?
                          aspectFactoryMethod.getDeclaringType() :
                         aspectFactoryMethod.getReturnType())
        .getSimpleName();
      }

      aspectFactories.add(
        new AspectFactoryImpl(aspectFactoryMethod, attributeName, isAspectFactoryReusable(aspectFactoryMethod))
      );
    }

    return aspectFactories;
  }

  protected int extractAspectOrder(HxType aspectType) {
    return aspectType.getAnnotation(Order.class)
                     .map(annotation -> annotation.getAttribute("value", StandardOrder.ASPECT))
                     .orElse(StandardOrder.ASPECT);
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

  protected List<HxMethod> findAspectFactories(final HxType aspectType) {
    final Collection<HxMethod> constructorFactories =
      aspectType.constructors(
        (c) -> c.isPublic() &&
               c.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));

    final Collection<HxMethod> methodFactories =
      aspectType.methods(
        (m) ->
          m.isPublic() &&
          m.isStatic() &&
          !m.getReturnType().isPrimitive() &&
          !m.getReturnType().isArray() &&
          m.isAnnotationPresent(ASPECT_FACTORY_ANNOTATION_TYPE));

    final ArrayList<HxMethod> factories = new ArrayList<>(constructorFactories.size() + methodFactories.size());
    factories.addAll(constructorFactories);
    factories.addAll(methodFactories);
    return factories;
  }

  protected boolean isAspectFactoryReusable(HxMethod aspectFactory) {
      return aspectFactory.getAnnotation(ASPECT_FACTORY_ANNOTATION_TYPE)
                          .map(annotation ->
                                 annotation.getAttribute("reuse", true))
                          .orElse(true);
  }
}
