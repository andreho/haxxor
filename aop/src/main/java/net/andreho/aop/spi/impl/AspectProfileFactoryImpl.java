package net.andreho.aop.spi.impl;

import net.andreho.aop.api.Profile;
import net.andreho.aop.api.Profiles;
import net.andreho.aop.api.spec.Site;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectProfileFactory;
import net.andreho.aop.spi.ElementMatcherFactory;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxEnum;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    final HxEnum siteEnum = profileAnnotation.getAttribute("site", (HxEnum) null);
    final boolean safely = profileAnnotation.getAttribute("safely", false);
    final HxType[] throwable = profileAnnotation.getAttribute("throwable", Constants.EMPTY_TYPE_ARRAY);
    final HxAnnotation[] methods = profileAnnotation.getAttribute("methods", Constants.EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] fields = profileAnnotation.getAttribute("fields", Constants.EMPTY_ANNOTATION_ARRAY);
    final HxAnnotation[] classes = profileAnnotation.getAttribute("classes", Constants.EMPTY_ANNOTATION_ARRAY);

    final Site site = siteEnum == null? Site.CALLEE : siteEnum.loadEnum(getClass().getClassLoader());

    result.add(
      new AspectProfileImpl(
        profileName,
        site,
        safely,
        Stream.of(throwable).map(HxType::getName).collect(Collectors.toSet()),
        elementMatcherFactory.createClassesFilter(classes),
        elementMatcherFactory.createMethodsFilter(methods),
        elementMatcherFactory.createFieldsFilter(fields)
      )
    );
  }
}
