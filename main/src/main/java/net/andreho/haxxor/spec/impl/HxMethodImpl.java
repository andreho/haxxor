package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Objects;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMethodImpl
    extends HxParameterizableImpl<HxMethod>
    implements HxMethod {

  protected final String name;
  protected HxType returnType;
  protected Object defaultValue;

  public HxMethodImpl(final String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Method-name can't be neither null nor empty.");
    }

    this.name = name;
    setModifiers(HxMethod.Modifiers.PUBLIC.toBit());
  }

  public HxMethodImpl(final String name,
                      final HxType returnType,
                      final HxType... parameters) {
    this(name);

    setReturnType(returnType);

    for (HxType type : parameters) {
      addParameterType(type);
    }
  }

  public HxMethodImpl(HxMethodImpl prototype) {
    this(prototype.name, prototype);
  }

  public HxMethodImpl(String name, HxMethodImpl prototype) {
    this.declaringMember = null;

    this.name = name != null? name : prototype.name;
    this.modifiers = prototype.modifiers;
    this.defaultValue = prototype.defaultValue;
    this.returnType = prototype.returnType;
    this.genericSignature = prototype.genericSignature;

    prototype.cloneParametersTo(this);
    prototype.cloneAnnotationsTo(this);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType getReturnType() {
    return returnType;
  }

  @Override
  public Object getDefaultValue() {
    return defaultValue;
  }

  @Override
  public HxMethod setReturnType(HxType returnType) {
    this.returnType = Objects.requireNonNull(returnType, "Return-type can't be null.");
    return this;
  }

  @Override
  public HxMethod setDefaultValue(Object value) {
    this.defaultValue = value;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxMethod)) {
      return false;
    }

    final HxMethod other = (HxMethod) o;

    if (!Objects.equals(this.getName(), other.getName())) {
      return false;
    }
    if (!Objects.equals(this.getReturnType(), other.getReturnType())) {
      return false;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return 31 * Objects.hashCode(getName()) + super.hashCode();
  }

  @Override
  public String toString() {
    if(getDeclaringMember() == null) {
      return getName() + super.toString() + getReturnType().getName();
    }
    return getDeclaringMember() + "." + getName() + super.toString() + getReturnType().getName();
  }
}
