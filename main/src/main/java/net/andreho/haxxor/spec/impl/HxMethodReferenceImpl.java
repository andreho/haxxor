package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxAnnotated;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxCode;
import net.andreho.haxxor.spec.HxMember;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxModifier;
import net.andreho.haxxor.spec.HxParameter;
import net.andreho.haxxor.spec.HxParameterizable;
import net.andreho.haxxor.spec.HxType;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxMethodReferenceImpl implements HxMethod {
   protected HxType declaringType;
   protected String name;
   protected HxType[] parameterTypes;
   protected HxType returnType;

   public HxMethodReferenceImpl(HxType declaringType, String name, HxType returnType, HxType... parameterTypes) {
      this.declaringType = declaringType;
      this.name = name;
      this.returnType = returnType;
      this.parameterTypes = parameterTypes;
   }

   public HxMethod get() {
      return declaringType.getMethod(name, parameterTypes);
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
   public String getName() {
      return name;
   }

   @Override
   public HxMethod setModifiers(HxModifier... modifiers) {
      get().setModifiers(modifiers);
      return this;
   }

   @Override
   public HxMethod setModifiers(int modifiers) {
      get().setModifiers(modifiers);
      return this;
   }

   @Override
   public int getModifiers() {
      return get().getModifiers();
   }

   @Override
   public HxType getReturnType() {
      //Because the return type doesn't identify a method itself
      return get().getReturnType();
   }

   @Override
   public HxMethod setReturnType(HxType returnType) {
      get().setReturnType(returnType);
      return this;
   }

   @Override
   public Object getDefaultValue() {
      return get().getDefaultValue();
   }

   @Override
   public HxMethod setDefaultValue(Object value) {
      get().setDefaultValue(value);
      return this;
   }

   @Override
   public List<HxParameter> getParameters() {
      return get().getParameters();
   }

   @Override
   public HxMethod setParameters(final List<HxParameter> parameters) {
      get().setParameters(parameters);
      return this;
   }

   @Override
   public List<HxType> getExceptionTypes() {
      return get().getExceptionTypes();
   }

   @Override
   public HxMethod setExceptionTypes(final List<HxType> exceptionTypes) {
      get().setExceptionTypes(exceptionTypes);
      return this;
   }

   @Override
   public HxMethod setParameterAt(final int index, final HxParameter parameter) {
      get().setParameterAt(index, parameter);
      return this;
   }

   @Override
   public HxMethod setExceptionTypes(HxType... exceptionTypes) {
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
   public HxMethod addAnnotation(HxAnnotation annotation) {
      get().addAnnotation(annotation);
      return this;
   }

   @Override
   public HxMethod setAnnotations(Collection<HxAnnotation> annotations) {
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
   public HxMethod setGenericSignature(String genericSignature) {
      get().setGenericSignature(genericSignature);
      return this;
   }

   @Override
   public <M extends HxMember> M getDeclaringMember() {
      return get().getDeclaringMember();
   }

   @Override
   public HxMethod setDeclaringMember(HxMember declaringMember) {
      get().setDeclaringMember(declaringMember);
      return this;
   }
}
