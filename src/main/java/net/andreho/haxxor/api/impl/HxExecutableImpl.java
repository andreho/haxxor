package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxMethodBody;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

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
  protected String genericSignature;
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
  public Hx getHaxxor() {
    return getDeclaringMember().getHaxxor();
  }

  @Override
  public int getIndex() {
    HxType type = getDeclaringMember();
    if(type == null) {
      return -1;
    }
    return type.indexOfMethod(this);
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

  protected List<HxParameter> initializeParameters() {
    return initializeParameters(0);
  }
  protected List<HxParameter> initializeParameters(int size) {
    List<HxParameter> parameters = this.parameters;
    if (isUninitialized(parameters)) {
      this.parameters = parameters = new ArrayList<>(size);
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
    return Optional.ofNullable(genericSignature);
  }

  @Override
  public HxMethod setGenericSignature(String genericSignature) {
    this.genericSignature = genericSignature;
    return this;
  }

  /**
   * @param predicate
   * to check on each overridden method (from a supertype or an implemented interface) until the test is successful
   * @return this instance
   */
  public Optional<HxMethod> visitOverriddenMethods(Predicate<HxMethod> predicate) {
    if(getDeclaringMember() == null) {
      return Optional.empty();
    }

    final AtomicReference<HxMethod> found = new AtomicReference<>();

    getDeclaringType().visitHierarchy((type) -> {
      if(!isDeclaredBy(type)) {
        Optional<HxMethod> methodOptional = type.findMethod(getName(), getParameterTypes());
        if(methodOptional.isPresent()) {
          HxMethod method = methodOptional.get();
          if(method.isOverridable() &&
             predicate.test(method)) {

            found.set(method);
            return true;
          }
        }
      }
      return false;
    });
    return Optional.ofNullable(found.get());
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
