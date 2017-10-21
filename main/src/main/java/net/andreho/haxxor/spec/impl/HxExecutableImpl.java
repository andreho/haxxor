package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
  protected Optional<String> genericSignature = Optional.empty();
  protected HxMethodBody body;

  public HxExecutableImpl() {
    super();
    this.setModifiers(Modifiers.PUBLIC.toBit());
  }

  protected void cloneParametersTo(final HxMethod other, boolean withAnnotations) {
    for (HxParameter parameter : getParameters()) {
      other.addParameter(parameter.clone(parameter.getName(), withAnnotations));
    }
  }

  protected void cloneCodeTo(final HxMethodImpl other) {
    if(hasBody()) {
      other.setBody(getBody().clone(other));
    } else {
      other.setBody(null);
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
  public boolean hasBody() {
    return this.body != null &&
           this.body.isAvailable();
  }

  @Override
  public HxMethodBody getBody() {
    HxMethodBody body = this.body;
    if (body == null) {
      this.body = body = new HxMethodBodyImpl(this);
    }
    return body;
  }

  @Override
  public HxMethod setBody(final HxMethodBody methodBody) {
    this.body = methodBody.clone(this);
    return this;
  }

  private List<HxParameter> initializeParameters() {
    List<HxParameter> parameters = this.parameters;
    if (isUninitialized(parameters)) {
      this.parameters = parameters = new ArrayList<>();
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
    if(getDeclaringType() == null) {
      return Collections.emptyList();
    }

    final String name = getName();
    final List<HxType> parameterTypes = getParameterTypes();
    final List<HxMethod> methods = new ArrayList<>();

    HxType current = getDeclaringType().getSuperType().orElse(null);

    while(current != null) {
      current.findMethod(name, parameterTypes).ifPresent(methods::add);
      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }

    current = getDeclaringType();

    while(current != null) {
      for(HxType itf : current.getInterfaces()) {
        Optional<HxMethod> method = itf.findMethod(name, parameterTypes);
        if(method.isPresent()) {
          methods.add(method.get());
        }
      }

      if(!current.hasSuperType()) {
        break;
      }
      current = current.getSuperType().get();
    }
    return methods;
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
    int hash = 0;
    for(HxParameter parameter : getParameters()) {
      hash = Integer.rotateLeft(hash, 1) + Objects.hashCode(parameter.getType());
    }
    return hash;
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
