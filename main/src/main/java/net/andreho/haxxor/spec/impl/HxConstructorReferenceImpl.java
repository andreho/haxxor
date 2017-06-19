package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxConstructorReferenceImpl
    implements HxConstructor {

  private final HxTypeReference declaringReference;
  private HxConstructor target;
  private String[] parameterTypes;

  public HxConstructorReferenceImpl(Haxxor haxxor,
                                    String declaringType,
                                    String... parameterTypes) {
    this.declaringReference = haxxor.reference(declaringType);
    this.parameterTypes = parameterTypes;
  }

  private boolean isAvailable() {
    return target != null;
  }

  public HxConstructor toConstructor() {
    if(target == null) {
      target = declaringReference.findConstructor(parameterTypes)
                                 .orElseThrow(() -> new IllegalStateException("Constructor not found."));
    }
    return target;
  }

  @Override
  public HxConstructor clone() {
    return toConstructor().clone();
  }

  @Override
  public int getIndex() {
    return toConstructor().getIndex();
  }

  @Override
  public Haxxor getHaxxor() {
    return declaringReference.getHaxxor();
  }

  @Override
  public boolean hasCode() {
    return toConstructor().hasCode();
  }

  @Override
  public HxCode getCode() {
    return toConstructor().getCode();
  }

  @Override
  public HxConstructor setModifiers(HxModifier... modifiers) {
    toConstructor().setModifiers(modifiers);
    return this;
  }

  @Override
  public HxConstructor setModifiers(int modifiers) {
    toConstructor().setModifiers(modifiers);
    return this;
  }

  @Override
  public int getModifiers() {
    return toConstructor().getModifiers();
  }

  @Override
  public List<HxParameter<HxConstructor>> getParameters() {
    return toConstructor().getParameters();
  }

  @Override
  public HxConstructor setParameters(final List<HxParameter<HxConstructor>> parameters) {
    toConstructor().setParameters(parameters);
    return this;
  }

  @Override
  public List<HxType> getExceptionTypes() {
    return toConstructor().getExceptionTypes();
  }

  @Override
  public HxConstructor setExceptionTypes(final List<HxType> exceptionTypes) {
    toConstructor().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxConstructor setParameterAt(final int index, final HxParameter parameter) {
    toConstructor().setParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxConstructor addParameter(final HxParameter<HxConstructor> parameter) {
    toConstructor().addParameter(parameter);
    return this;
  }

  @Override
  public HxConstructor addParameterAt(final int index, final HxParameter<HxConstructor> parameter) {
    toConstructor().addParameterAt(index, parameter);
    return this;
  }

  @Override
  public HxConstructor setExceptionTypes(HxType... exceptionTypes) {
    toConstructor().setExceptionTypes(exceptionTypes);
    return this;
  }

  @Override
  public HxParameter getParameterAt(final int index) {
    return toConstructor().getParameterAt(index);
  }


  @Override
  public Collection<HxExecutable> getOverriddenMembers() {
    return toConstructor().getOverriddenMembers();
  }

  @Override
  public HxConstructor addAnnotation(HxAnnotation annotation) {
    toConstructor().addAnnotation(annotation);
    return this;
  }

  @Override
  public HxConstructor setAnnotations(Collection<HxAnnotation> annotations) {
    toConstructor().setAnnotations(annotations);
    return this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return toConstructor().getSuperAnnotated();
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return toConstructor().getAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(String type) {
    return toConstructor().isAnnotationPresent(type);
  }

  @Override
  public Optional<HxAnnotation> getAnnotation(String type) {
    return toConstructor().getAnnotation(type);
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(String type) {
    return toConstructor().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive) {
    return toConstructor().annotations(predicate, recursive);
  }

  @Override
  public Optional<String> getGenericSignature() {
    return toConstructor().getGenericSignature();
  }

  @Override
  public HxConstructor setGenericSignature(String genericSignature) {
    toConstructor().setGenericSignature(genericSignature);
    return this;
  }

  @Override
  public HxType getDeclaringMember() {
    if(!isAvailable()) {
      return this.declaringReference;
    }
    return toConstructor().getDeclaringMember();
  }

  @Override
  public HxConstructor setDeclaringMember(HxMember declaringMember) {
    toConstructor().setDeclaringMember(declaringMember);
    return this;
  }
}
