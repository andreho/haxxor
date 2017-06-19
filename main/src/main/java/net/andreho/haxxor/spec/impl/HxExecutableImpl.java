package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
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
public abstract class HxExecutableImpl
    extends HxAnnotatedImpl<HxMethod>
    implements HxMethod {

  protected static final List DEFAULT_EMPTY_PARAMETERS = Collections.emptyList();
  protected static final List DEFAULT_EMPTY_EXCEPTIONS = Collections.emptyList();

  protected List<HxParameter> parameters = DEFAULT_EMPTY_PARAMETERS;
  protected List<HxType> exceptions = DEFAULT_EMPTY_EXCEPTIONS;
  protected Optional<String> genericSignature;
  protected HxCode code;

  public HxExecutableImpl() {
    super();
    this.setModifiers(Modifiers.PUBLIC.toBit());
  }

  protected void cloneParametersTo(HxMethod other) {
    for (HxParameter parameter : getParameters()) {
      other.addParameter(parameter.clone());
    }
  }

  public abstract HxMethod clone();

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
    return type.indexOf(this);
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

  private List<HxParameter> initializeParameters() {
    if (isUninitialized(parameters)) {
      parameters = new ArrayList<>();
    }
    return parameters;
  }

  private HxParameter applyParameter(HxParameter parameter) {
    if (parameter.getDeclaringMember() != null) {
      throw new IllegalArgumentException(
          "Parameter is already in use, please clone it before further usage: " +
          parameter);
    }
    parameter.setDeclaringMember(this);
    return parameter;
  }

  protected List<HxParameter> applyParameters(List<HxParameter> parameters) {
    for (HxParameter parameter : parameters) {
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
  public List<HxParameter> getParameters() {
    return this.parameters;
  }

  @Override
  public HxMethod setParameters(final List<HxParameter> parameters) {
    this.parameters = applyParameters(parameters);
    return this;
  }

  @Override
  public HxMethod setExceptionTypes(List<HxType> exceptionTypes) {
    this.exceptions = exceptionTypes;
    return this;
  }

  @Override
  public HxMethod addParameterAt(int index,
                          final HxParameter parameter) {
    initializeParameters().add(index, applyParameter(parameter));
    return this;
  }

  @Override
  public HxParameter getParameterAt(final int index) {
    return parameters.get(index);
  }

  @Override
  public HxMethod setParameterAt(final int index, final HxParameter parameter) {
    HxParameter oldParameter = getParameterAt(index);
    initializeParameters().set(index, applyParameter(parameter));
    oldParameter.setDeclaringMember(null);
    return this;
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
  public HxMethod setGenericSignature(String genericSignature) {
    if(genericSignature == null || genericSignature.isEmpty()) {
      this.genericSignature = Optional.empty();
    } else {
      this.genericSignature = Optional.of(genericSignature);
    }
    return this;
  }

  @Override
  public Collection<HxMethod> getOverriddenMembers() {
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
    if (!(o instanceof HxMethod)) {
      return false;
    }

    final HxMethod other = (HxMethod) o;
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
