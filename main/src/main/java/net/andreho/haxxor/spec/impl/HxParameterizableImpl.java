package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod.Modifiers;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxParameterizable;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public abstract class HxParameterizableImpl<P extends HxParameterizable<P>>
    extends HxAnnotatedImpl<P>
    implements HxParameterizable<P> {

  protected List<HxParameter<P>> parameters;
  protected List<HxType> exceptions;
  protected String genericSignature;
  protected HxCode code;

  public HxParameterizableImpl() {
    super();
    this.setModifiers(Modifiers.PUBLIC.toBit());
  }

  @Override
  public Haxxor getHaxxor() {
    return ((HxType) getDeclaringMember()).getHaxxor();
  }

  @Override
  public boolean hasCode() {
    return this.code != null && this.code.isAvailable();
  }

  @Override
  public HxCode getCode() {
    if (this.code == null) {
      this.code = new HxCodeImpl(this);
    }

    return this.code;
  }

  @Override
  public List<HxParameter<P>> getParameters() {
    return this.parameters;
  }

  @Override
  public P setParameters(final List<HxParameter<P>> parameters) {
    this.parameters = parameters;
    return (P) this;
  }

  @Override
  public P setExceptionTypes(List<HxType> exceptionTypes) {
    this.exceptions = exceptionTypes;
    return (P) this;
  }

  @Override
  public HxParameter<P> getParameterAt(final int index) {
    return parameters.get(index);
  }

  @Override
  public P setParameterAt(final int index, final HxParameter<P> parameter) {
    this.parameters.set(index, parameter);
    parameter.setDeclaringMember(this);
    return (P) this;
  }

  @Override
  public P addParameterAt(final int index, final HxParameter<P> parameter) {
    this.parameters.add(index, parameter);
    parameter.setDeclaringMember(this);
    return (P) this;
  }

  @Override
  public List<HxType> getExceptionTypes() {
    return exceptions;
  }

  @Override
  public String getGenericSignature() {
    return genericSignature;
  }

  @Override
  public P setGenericSignature(String genericSignature) {
    this.genericSignature = Objects.requireNonNull(genericSignature);
    return (P) this;
  }

  @Override
  public Collection<HxParameterizable> getOverriddenMembers() {
    return Collections.emptySet();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxParameterizable)) {
      return false;
    }

    final HxParameterizable other = (HxParameterizable) o;
    return getParameters().equals(other.getParameters());
  }

  @Override
  public int hashCode() {
    return getParameters().hashCode();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("(");

    if (getArity() > 0) {
      HxType type = getParameterTypeAt(0);
      builder.append(type.getJavaName());

      for (int i = 1, arity = getArity(); i < arity; i++) {
        builder.append(',');
        type = getParameterTypeAt(i);
        builder.append(type.getJavaName());
      }
    }

    return builder.append(')')
                  .toString();
  }
}
