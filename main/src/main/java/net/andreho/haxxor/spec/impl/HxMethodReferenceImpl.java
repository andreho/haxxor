package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMethodReferenceImpl
    implements HxMethod {

  protected HxType declaringType;
  protected String name;
  protected String returnType;
  protected String[] parameterTypes;
  protected HxMethod method;

  public HxMethodReferenceImpl(Haxxor haxxor,
                               String declaringType,
                               String methodName,
                               String returnType,
                               String...parameterTypes) {

    this.declaringType = haxxor.reference(declaringType);
    this.name = methodName;
    this.returnType = returnType;
    this.parameterTypes = parameterTypes;
  }

  private boolean isAvailable() {
    return this.method != null;
  }

  public HxMethod toMethod() {
    HxMethod method = this.method;
    if (method == null) {
      this.method = method =
        declaringType.findMethod(returnType, name, parameterTypes)
                     .orElseThrow(this::complainAboutMissingMethod);
    }
    return method;
  }

  private IllegalStateException complainAboutMissingMethod() {
    final StringBuilder builder = new StringBuilder("Method not found: ");
    builder.append(declaringType).append(".").append(name).append('(');

    if(parameterTypes.length > 0) {
      builder.append(parameterTypes[0]);
      for (int i = 1; i < parameterTypes.length; i++) {
        builder.append(',').append(parameterTypes[i]);
      }
    }
    return new IllegalStateException(builder.append(')').append(returnType).toString());
  }

  @Override
  public HxMethod clone() {
    return clone(getName());
  }

  @Override
  public HxMethod clone(String name) {
    return toMethod().clone(name);
  }

  @Override
  public HxMethod clone(final String name,
                        final int parts) {
    return toMethod().clone(name, parts);
  }

  @Override
  public HxMethod setBody(final HxMethodBody methodBody) {
    return null;
  }

  @Override
  public Haxxor getHaxxor() {
    return declaringType.getHaxxor();
  }

  @Override
  public int getIndex() {
    return toMethod().getIndex();
  }

  @Override
  public boolean hasBody() {
    return toMethod().hasBody();
  }

  @Override
  public HxMethodBody getBody() {
    return toMethod().getBody();
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxMethod setModifiers(int modifiers) {
    toMethod().setModifiers(modifiers);
    return this;
  }

  @Override
  public int getModifiers() {
    return toMethod().getModifiers();
  }

  @Override
  public HxType getReturnType() {
    //Because the return type doesn't identify a method itself
    return toMethod().getReturnType();
  }

  @Override
  public HxMethod setReturnType(HxType returnType) {
    toMethod().setReturnType(returnType);
    return this;
  }

  @Override
  public Object getDefaultValue() {
    return toMethod().getDefaultValue();
  }

  @Override
  public HxMethod setDefaultValue(Object value) {
    toMethod().setDefaultValue(value);
    return this;
  }

  @Override
  public List<HxParameter> getParameters() {
    return toMethod().getParameters();
  }

  @Override
  public HxMethod setParameters(final List<HxParameter> parameters) {
    toMethod().setParameters(parameters);
    return this;
  }

  @Override
  public List<HxType> getExceptionTypes() {
    return toMethod().getExceptionTypes();
  }

  @Override
  public HxMethod setExceptionTypes(final List<HxType> exceptionTypes) {
    toMethod().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxMethod addParameter(final HxParameter parameter) {
    toMethod().addParameter(parameter);
    return this;
  }

  @Override
  public HxMethod addParameterAt(final int index, final HxParameter parameter) {
    toMethod().addParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxMethod setParameterAt(final int index, final HxParameter parameter) {
    toMethod().setParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxMethod setExceptionTypes(HxType... exceptionTypes) {
    toMethod().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxParameter getParameterAt(final int index) {
    return toMethod().getParameterAt(index);
  }

  @Override
  public Collection<HxMethod> getOverriddenMembers() {
    return toMethod().getOverriddenMembers();
  }

  @Override
  public HxMethod addAnnotation(HxAnnotation annotation) {
    toMethod().addAnnotation(annotation);
    return this;
  }

  @Override
  public HxMethod setAnnotations(Collection<HxAnnotation> annotations) {
    toMethod().setAnnotations(annotations);
    return this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return toMethod().getSuperAnnotated();
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return toMethod().getAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(String type) {
    return toMethod().isAnnotationPresent(type);
  }

  @Override
  public Optional<HxAnnotation> getAnnotation(String type) {
    return toMethod().getAnnotation(type);
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(String type) {
    return toMethod().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive) {
    return toMethod().annotations(predicate, recursive);
  }

  @Override
  public Optional<String> getGenericSignature() {
    return toMethod().getGenericSignature();
  }

  @Override
  public HxMethod setGenericSignature(String genericSignature) {
    toMethod().setGenericSignature(genericSignature);
    return this;
  }

  @Override
  public HxType getDeclaringMember() {
    if(!isAvailable()) {
      return declaringType;
    }
    return toMethod().getDeclaringMember();
  }

  @Override
  public HxMethod setDeclaringMember(HxMember declaringMember) {
    toMethod().setDeclaringMember(declaringMember);
    return this;
  }
}
