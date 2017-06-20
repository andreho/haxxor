package net.andreho.aop.spi.impl;

import net.andreho.aop.api.spec.Site;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:30.
 */
public class AspectProfileImpl
  implements AspectProfile {

  private final String name;
  private final Site site;
  private final boolean safely;
  private final Set<String> throwableSet;
  private final ElementMatcher<HxMethod> methodsMatcher;
  private final ElementMatcher<HxField> fieldsMatcher;
  private final ElementMatcher<HxType> classesMatcher;

  public AspectProfileImpl(final String name,
                           final Site site,
                           final boolean safely,
                           final Set<String> throwableSet,
                           final ElementMatcher<HxType> classesMatcher,
                           final ElementMatcher<HxMethod> methodsMatcher,
                           final ElementMatcher<HxField> fieldsMatcher) {
    this.name = requireNonNull(name);
    this.site = requireNonNull(site);
    this.safely = safely;
    this.throwableSet = (throwableSet == null || throwableSet.isEmpty())?
                        emptySet() :
                        unmodifiableSet(throwableSet);

    this.classesMatcher = requireNonNull(classesMatcher).minimize();
    this.methodsMatcher = requireNonNull(methodsMatcher).minimize();
    this.fieldsMatcher = requireNonNull(fieldsMatcher).minimize();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Site getSite() {
    return site;
  }

  @Override
  public boolean isSafely() {
    return safely;
  }

  @Override
  public Set<String> getThrowableSet() {
    return throwableSet;
  }

  @Override
  public ElementMatcher<HxMethod> getMethodsMatcher() {
    return methodsMatcher;
  }

  @Override
  public ElementMatcher<HxField> getFieldsMatcher() {
    return fieldsMatcher;
  }

  @Override
  public ElementMatcher<HxType> getClassesMatcher() {
    return classesMatcher;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final AspectProfileImpl that = (AspectProfileImpl) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return "@Profile(name=\"" + name + "\")";
  }
}
