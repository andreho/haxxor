package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxFieldImpl
    extends HxAnnotatedImpl<HxField>
    implements HxField {

  protected final String name;
  protected HxType type;
  protected Object defaultValue;
  protected Optional<String> genericSignature = Optional.empty();

  public HxFieldImpl(HxType type,
                     String name) {
    super();
    this.setModifiers(HxMethod.Modifiers.PUBLIC.toBit());

    if (type == null) {
      throw new IllegalArgumentException("Type is null.");
    }

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name is either null or empty.");
    }

    this.type = type;
    this.name = name;
  }

  public HxFieldImpl(HxType declaringType,
                     String name,
                     HxType type) {
    this(type, name);

    if (declaringType == null) {
      throw new IllegalArgumentException("Declaring owner can't be null.");
    }
    this.declaringMember = declaringType;
  }

  protected HxFieldImpl(String name, HxFieldImpl prototype) {

    this.declaringMember = null;

    this.name = name != null? name : prototype.name;
    this.type = prototype.type;
    this.modifiers = prototype.modifiers;
    this.defaultValue = prototype.defaultValue;
    this.genericSignature = prototype.genericSignature;

    prototype.cloneAnnotationsTo(this);
  }

  @Override
  public Hx getHaxxor() {
    return this.type.getHaxxor();
  }

  @Override
  public HxField clone() {
    return clone(getName());
  }

  @Override
  public HxField clone(final String name) {
    return new HxFieldImpl(name, this);
  }

  @Override
  public int getIndex() {
    HxType hxType = getDeclaringMember();
    if(hxType == null) {
      return -1;
    }
    return hxType.indexOf(this);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType getType() {
    return type;
  }

  @Override
  public HxField setType(HxType type) {
    if (type == null) {
      throw new IllegalArgumentException("Field's type can't be null");
    }

    this.type = type;
    return this;
  }

  @Override
  public Object getDefaultValue() {
    return defaultValue;
  }

  @Override
  public HxField setDefaultValue(Object value) {
    this.defaultValue = value;
    return this;
  }

  @Override
  public Optional<String> getGenericSignature() {
    return genericSignature;
  }

  @Override
  public HxField setGenericSignature(String genericSignature) {
    if(genericSignature == null || genericSignature.isEmpty()) {
      this.genericSignature = Optional.empty();
    } else {
      this.genericSignature = Optional.of(genericSignature);
    }
    return this;
  }

  @Override
  public HxType getDeclaringMember() {
    return (HxType) super.getDeclaringMember();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxField)) {
      return false;
    }

    HxField other = (HxField) o;

    if(!Objects.equals(getName(), other.getName())) {
      return false;
    }
    if(!Objects.equals(getType(), other.getType())) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getName()) +
           Objects.hashCode(getType()) * 31;
  }

  @Override
  public String toString() {
    String declaring =
        getDeclaringMember() == null ?
        HxConstants.UNDEFINED_TYPE :
        getDeclaringMember().toString();

    return declaring + "." + getName();
  }
}
