package net.andreho.aop.spi.impl;

import net.andreho.aop.api.Profile;
import net.andreho.aop.api.Profiles;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectProfileFactory;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxType;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 03:20.
 */
public class AspectProfileFactoryImpl implements AspectProfileFactory {
  private final ElementMatcherFactory elementMatcherFactory;

  public AspectProfileFactoryImpl(final ElementMatcherFactory elementMatcherFactory) {
    this.elementMatcherFactory = elementMatcherFactory;
  }

  @Override
  public Collection<AspectProfile> create(final HxType aspectType) {
    final Collection<AspectProfile> profiles = new LinkedHashSet<>();
    Optional<HxAnnotation> annotation = aspectType.getAnnotation(Profile.class);

    if(annotation.isPresent()) {
      createAspectProfile(annotation.get(), profiles);
    } else if((annotation = aspectType.getAnnotation(Profiles.class)).isPresent()) {
      createAspectProfiles(annotation.get().getAttribute("value", Constants.EMPTY_ANNOTATION_ARRAY), profiles);
    }

    return profiles;
  }

  protected void createAspectProfiles(final HxAnnotation[] profileAnnotations, final Collection<AspectProfile> result) {
    for(HxAnnotation profile : profileAnnotations) {
      createAspectProfile(profile, result);
    }
  }

  protected void createAspectProfile(final HxAnnotation profileAnnotation, final Collection<AspectProfile> result) {
    final String profileName = profileAnnotation.getAttribute("name", "");

    if(profileName.isEmpty()) {
      return;
    }

    final HxAnnotation[] classes = profileAnnotation.getAttribute("classes", Constants.EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] fields = profileAnnotation.getAttribute("fields", Constants.EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] methods = profileAnnotation.getAttribute("methods", Constants.EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] parameters = profileAnnotation.getAttribute("parameters", Constants.EMPTY_ANNOTATION_ARRAY);

    result.add(
      new AspectProfileImpl(
        profileName,
        elementMatcherFactory.createClassesFilter(classes),
        elementMatcherFactory.createMethodsFilter(methods),
        elementMatcherFactory.createFieldsFilter(fields),
        elementMatcherFactory.createParametersFilter(parameters)
      )
    );
  }
}
