package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxMethod.Modifiers;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public abstract class HxExecutableImpl<P extends HxExecutable<P>>
    extends HxAnnotatedImpl<P>
    implements HxExecutable<P> {

  protected static final List DEFAULT_EMPTY_PARAMETERS = Collections.emptyList();
  protected static final List DEFAULT_EMPTY_EXCEPTIONS = Collections.emptyList();

  protected List<HxParameter<P>> parameters = DEFAULT_EMPTY_PARAMETERS;
  protected List<HxType> exceptions = DEFAULT_EMPTY_EXCEPTIONS;
  protected Optional<String> genericSignature;
  protected HxCode code;

  public HxExecutableImpl() {
    super();
    this.setModifiers(Modifiers.PUBLIC.toBit());
  }

  protected void cloneParametersTo(HxExecutable<P> other) {
    for (HxParameter<P> parameter : getParameters()) {
      other.addParameter(parameter.clone());
    }
  }

  @Override
  public Haxxor getHaxxor() {
    return getDeclaringMember().getHaxxor();
  }

  @Override
  public int getIndex() {
    HxType type = getDeclaringMember();
    if(type == null) {
      return -1;
    }
    if(this instanceof HxConstructor) {
      return type.indexOf((HxConstructor) this);
    }
    return type.indexOf((HxConstructor) this);
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

  private List<HxParameter<P>> initializeParameters() {
    if (isUninitialized(parameters)) {
      parameters = new ArrayList<>();
    }
    return parameters;
  }

  private HxParameter<P> applyParameter(HxParameter<P> parameter) {
    if (parameter.getDeclaringMember() != null) {
      throw new IllegalArgumentException(
          "Parameter is already in use, please clone it before further usage: " +
          parameter);
    }
    parameter.setDeclaringMember(this);
    return parameter;
  }

  protected List<HxParameter<P>> applyParameters(List<HxParameter<P>> parameters) {
    for (HxParameter<P> parameter : parameters) {
      applyParameter(parameter);
    }
    return parameters;
  }

  private List<HxType> initializeExceptions() {
    if (isUninitialized(exceptions)) {
      exceptions = new ArrayList<>();
    }
    return exceptions;
  }

  @Override
  public List<HxParameter<P>> getParameters() {
    return this.parameters;
  }

  @Override
  public P setParameters(final List<HxParameter<P>> parameters) {
    this.parameters = applyParameters(parameters);
    return (P) this;
  }

  @Override
  public P setExceptionTypes(List<HxType> exceptionTypes) {
    this.exceptions = exceptionTypes;
    return (P) this;
  }

  @Override
  public P addParameterAt(int index,
                          final HxParameter<P> parameter) {
    initializeParameters().add(index, applyParameter(parameter));
    return (P) this;
  }

  @Override
  public HxParameter<P> getParameterAt(final int index) {
    return parameters.get(index);
  }

  @Override
  public P setParameterAt(final int index, final HxParameter<P> parameter) {
    HxParameter<P> oldParameter = getParameterAt(index);
    initializeParameters().set(index, applyParameter(parameter));
    oldParameter.setDeclaringMember(null);
    return (P) this;
  }

  @Override
  public List<HxType> getExceptionTypes() {
    return exceptions;
  }

  @Override
  public Optional<String> getGenericSignature() {
    return genericSignature;
  }

  @Override
  public P setGenericSignature(String genericSignature) {
    if(genericSignature == null || genericSignature.isEmpty()) {
      this.genericSignature = Optional.empty();
    } else {
      this.genericSignature = Optional.of(genericSignature);
    }
    return (P) this;
  }

  @Override
  public Collection<HxExecutable> getOverriddenMembers() {
    return Collections.emptySet();
  }

  @Override
  public HxType getDeclaringMember() {
    return (HxType) super.getDeclaringMember();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HxExecutable)) {
      return false;
    }

    final HxExecutable other = (HxExecutable) o;
    return getParameters().equals(other.getParameters());
  }

  @Override
  public int hashCode() {
    return getParameters().hashCode();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("(");

    if (getParametersCount() > 0) {
      HxType type = getParameterTypeAt(0);
      builder.append(type.getName());

      for (int i = 1, arity = getParametersCount(); i < arity; i++) {
        builder.append(',');
        type = getParameterTypeAt(i);
        builder.append(type.getName());
      }
    }

    return builder.append(')')
                  .toString();
  }
}
