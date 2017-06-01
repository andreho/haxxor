package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxParameterizable;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorReferenceImpl
    implements HxConstructor {

  private final HxType declaringType;
  private HxConstructor target;
  private HxType[] parameterTypes;

  public HxConstructorReferenceImpl(HxType declaringType,
                                    HxType... parameterTypes) {
    this.declaringType = declaringType;
    this.parameterTypes = parameterTypes;
  }

  public HxConstructor get() {
    if(target == null) {
      target = declaringType.getConstructor(parameterTypes);
    }
    return target;
  }

  @Override
  public HxConstructor clone() {
    return get().clone();
  }

  @Override
  public Haxxor getHaxxor() {
    return declaringType.getHaxxor();
  }

  @Override
  public boolean hasCode() {
    return get().hasCode();
  }

  @Override
  public HxCode getCode() {
    return get().getCode();
  }

  @Override
  public HxConstructor setModifiers(HxModifier... modifiers) {
    get().setModifiers(modifiers);
    return this;
  }

  @Override
  public HxConstructor setModifiers(int modifiers) {
    get().setModifiers(modifiers);
    return this;
  }

  @Override
  public int getModifiers() {
    return get().getModifiers();
  }

  @Override
  public List<HxParameter<HxConstructor>> getParameters() {
    return get().getParameters();
  }

  @Override
  public HxConstructor setParameters(final List<HxParameter<HxConstructor>> parameters) {
    get().setParameters(parameters);
    return this;
  }

  @Override
  public List<HxType> getExceptionTypes() {
    return get().getExceptionTypes();
  }

  @Override
  public HxConstructor setExceptionTypes(final List<HxType> exceptionTypes) {
    get().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxConstructor setParameterAt(final int index, final HxParameter parameter) {
    get().setParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxConstructor addParameter(final HxParameter<HxConstructor> parameter) {
    get().addParameter(parameter);
    return this;
  }

  @Override
  public HxConstructor addParameterAt(final int index, final HxParameter<HxConstructor> parameter) {
    get().addParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxConstructor setExceptionTypes(HxType... exceptionTypes) {
    get().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxParameter getParameterAt(final int index) {
    return get().getParameterAt(index);
  }


  @Override
  public Collection<HxParameterizable> getOverriddenMembers() {
    return get().getOverriddenMembers();
  }

  @Override
  public HxConstructor addAnnotation(HxAnnotation annotation) {
    get().addAnnotation(annotation);
    return this;
  }

  @Override
  public HxConstructor setAnnotations(Collection<HxAnnotation> annotations) {
    get().setAnnotations(annotations);
    return this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return get().getSuperAnnotated();
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return get().getAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(String type) {
    return get().isAnnotationPresent(type);
  }

  @Override
  public HxAnnotation getAnnotation(String type) {
    return get().getAnnotation(type);
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(String type) {
    return get().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive) {
    return get().annotations(predicate, recursive);
  }

  @Override
  public String getGenericSignature() {
    return get().getGenericSignature();
  }

  @Override
  public HxConstructor setGenericSignature(String genericSignature) {
    get().setGenericSignature(genericSignature);
    return this;
  }

  @Override
  public <M extends HxMember> M getDeclaringMember() {
    return get().getDeclaringMember();
  }

  @Override
  public HxConstructor setDeclaringMember(HxMember declaringMember) {
    get().setDeclaringMember(declaringMember);
    return this;
  }
}
