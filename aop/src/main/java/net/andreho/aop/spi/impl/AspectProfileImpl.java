package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 20.06.2017 at 02:30.
 */
public class AspectProfileImpl
  implements AspectProfile {

  private final String name;
  private final ElementMatcher<HxParameter> parametersMatcher;
  private final ElementMatcher<HxMethod> methodsMatcher;
  private final ElementMatcher<HxField> fieldsMatcher;
  private final ElementMatcher<HxType> classesMatcher;

  public AspectProfileImpl(final String name,
                           final ElementMatcher<HxType> classesMatcher,
                           final ElementMatcher<HxMethod> methodsMatcher,
                           final ElementMatcher<HxField> fieldsMatcher,
                           final ElementMatcher<HxParameter> parametersMatcher) {
    this.name = requireNonNull(name);

    this.classesMatcher = requireNonNull(classesMatcher).minimize();
    this.methodsMatcher = requireNonNull(methodsMatcher).minimize();
    this.fieldsMatcher = requireNonNull(fieldsMatcher).minimize();
    this.parametersMatcher = requireNonNull(parametersMatcher).minimize();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ElementMatcher<HxParameter> getParametersMatcher() {
    return parametersMatcher;
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
    if (!(o instanceof AspectProfile)) {
      return false;
    }
    return name.equals(((AspectProfile) o).getName());
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
