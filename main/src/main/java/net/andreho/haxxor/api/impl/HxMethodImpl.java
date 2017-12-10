package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static net.andreho.haxxor.api.impl.HxImplTools.checkDescriptorsParameters;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMethodImpl
  extends HxExecutableImpl
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

  protected HxMethodImpl(final HxMethodImpl prototype,
                         final String name,
                         final int parts) {
    this.declaringMember = null;

    this.name = Objects.requireNonNull(name != null ? name : prototype.name, "Method's name can't be null.");
    this.modifiers = prototype.modifiers;
    this.defaultValue = prototype.defaultValue;
    this.returnType = prototype.returnType;
    this.genericSignature = prototype.genericSignature;
    this.exceptions = prototype.hasCheckedExceptions() ?
                      new ArrayList<>(prototype.getExceptionTypes()) :
                      Collections.emptyList();

    prototype.cloneParametersTo(this, 0 != (parts & CloneableParts.PARAMETERS_ANNOTATIONS_PART));

    if (0 != (parts & CloneableParts.ANNOTATIONS_PART)) {
      prototype.cloneAnnotationsTo(this);
    }

    if (0 != (parts & CloneableParts.BODY_PART)) {
      prototype.cloneCodeTo(this);
    }
  }

  @Override
  public HxMethod clone() {
    return clone(getName());
  }

  @Override
  public HxMethod clone(String name) {
    return clone(name, HxMethod.CloneableParts.ALL_PARTS);
  }

  @Override
  public HxMethod clone(final String name,
                        final int parts) {
    return new HxMethodImpl(this, name, parts);
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
    this.defaultValue = Objects.requireNonNull(
      value,
      "Default values on methods are only used together with annotations and can't be null."
    );
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
    return Objects.hashCode(getName()) +
           (31 * super.hashCode()) +
           (31 * Objects.hashCode(getReturnType()));
  }

  @Override
  public String toString() {
    return (getDeclaringMember() == null ? HxConstants.UNDEFINED_TYPE : getDeclaringMember()) + "."
           + getName() + super.toString() + getReturnType().getName();
  }

  @Override
  public boolean hasDescriptor(final String descriptor) {
    final int arity = this.getParametersCount();
    final int returnIndex = descriptor.lastIndexOf(')');
    final int length = descriptor.length();

    if (descriptor.charAt(0) != '(' || returnIndex < 1) {
      throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
    }

    int parameters = 0;
    if (descriptor.charAt(1) != ')') {
      if (arity == 0) {
        return false;
      }
      for (int i = 1; i < returnIndex; ) {
        if (parameters >= arity) {
          return false;
        }
        int result = checkDescriptorsParameters(this.getParameterTypeAt(parameters), descriptor, i, returnIndex);
        if (result < 0) {
          return false;
        } else {
          parameters++;
        }
        i = result;
      }
    }

    if (parameters != arity) {
      return false;
    }

    return checkDescriptorsParameters(this.getReturnType(), descriptor, returnIndex + 1, length) >= 0;
  }
}
