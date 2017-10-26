package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

import java.util.Objects;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxParameterImpl
    extends HxAnnotatedImpl<HxParameter>
    implements
    HxParameter {

  private String name;
  private HxType type;

  public HxParameterImpl() {
  }

  public HxParameterImpl(final HxType type) {
    this.setType(type);
  }

  public HxParameterImpl(final HxParameterImpl prototype) {
    this(prototype, prototype.name, true);
  }

  public HxParameterImpl(final HxParameterImpl prototype,
                         final String name,
                         final boolean withAnnotations) {
    this.declaringMember = null;

    this.modifiers = prototype.modifiers;
    this.type = prototype.type;
    this.name = name != null? name : prototype.name;

    if(withAnnotations) {
      prototype.cloneAnnotationsTo(this);
    }
  }

  @Override
  public HxParameter clone() {
    return clone(getName(), true);
  }

  @Override
  public HxParameter clone(final String name) {
    return clone(getName(), true);
  }

  @Override
  public HxParameter clone(final String name,
                           final boolean withAnnotations) {
    return new HxParameterImpl(this, name, withAnnotations);
  }

  @Override
  public Hx getHaxxor() {
    HxType type = getType();
    if (type != null) {
      return type.getHaxxor();
    }
    return null;
  }

  @Override
  public int getIndex() {
    HxMethod declaringMember = getDeclaringMember();
    if (declaringMember == null) {
      return -1;
    }
    return declaringMember.getParameters()
                          .indexOf(this);
  }

  @Override
  public String getName() {
    final String name = this.name;
    if (name != null) {
      return name;
    }
    int index = getIndex();
    return "arg" + (index > -1 ? index : "X");
  }

  @Override
  public HxParameter setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean isNamePresent() {
    return this.name != null;
  }

  @Override
  public boolean isImplicit() {
    return hasModifiers(Modifiers.MANDATED);
  }

  @Override
  public boolean isSynthetic() {
    return hasModifiers(Modifiers.SYNTHETIC);
  }

  @Override
  public boolean isVarArgs() {
    HxMethod declaringMember = getDeclaringMember();
    if(declaringMember != null) {
      return declaringMember.isVarArg() &&
             (declaringMember.getParametersCount() - 1) == getIndex();
    }
    return false;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public HxParameter setType(final HxType type) {
    this.type = Objects.requireNonNull(type, "Parameter-type can't be null.");
    if("void".equals(type.getName())) {
      throw new IllegalArgumentException("Parameter's type can't be void.");
    }
    return this;
  }

  @Override
  public HxMethod getDeclaringMember() {
    return (HxMethod) super.getDeclaringMember();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxParameter)) {
      return false;
    }
    final HxParameter other = (HxParameter) o;
    return Objects.equals(getType(), other.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getType());
  }

  @Override
  public String toString() {
    HxType type = getType();
    return (type == null ? HxConstants.UNDEFINED_TYPE : type.getName()) + " " + getName();
  }
}
