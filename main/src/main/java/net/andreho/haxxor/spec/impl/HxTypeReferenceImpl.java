package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.HxAnnotated;
import net.andreho.haxxor.spec.HxAnnotation;
import net.andreho.haxxor.spec.HxConstructor;
import net.andreho.haxxor.spec.HxField;
import net.andreho.haxxor.spec.HxMember;
import net.andreho.haxxor.spec.HxMethod;
import net.andreho.haxxor.spec.HxModifier;
import net.andreho.haxxor.spec.HxType;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxTypeReferenceImpl extends HxAbstractType {
   public HxTypeReferenceImpl(Haxxor haxxor, String name) {
      this(haxxor, name, -1);
   }

   public HxTypeReferenceImpl(Haxxor haxxor, String name, int modifiers) {
      super(haxxor, name);
      this.modifiers = modifiers;
   }

   //----------------------------------------------------------------------------------------------------------------

   public HxType get() {
      return getHaxxor().resolve(getName());
   }

   @Override
   public Version getVersion() {
      return get().getVersion();
   }

   @Override
   public HxType setVersion(Version version) {
      get().setVersion(version);
      return this;
   }

   //----------------------------------------------------------------------------------------------------------------

   @Override
   public boolean isPrimitive() {
      return get().isPrimitive();
   }

   @Override
   public boolean isArray() {
      return get().isArray();
   }

   @Override
   public boolean isGeneric() {
      return get().isGeneric();
   }

   @Override
   public int getDimension() {
      return get().getDimension();
   }

   @Override
   public boolean isLocalType() {
      return get().isLocalType();
   }

   @Override
   public boolean isMemberType() {
      return get().isMemberType();
   }

   @Override
   public boolean isFinal() {
      return get().isFinal();
   }

   @Override
   public boolean isPublic() {
      return get().isPublic();
   }

   @Override
   public boolean isProtected() {
      return get().isProtected();
   }

   @Override
   public boolean isPrivate() {
      return get().isPrivate();
   }

   @Override
   public boolean isInternal() {
      return get().isInternal();
   }

   @Override
   public boolean isAbstract() {
      return get().isAbstract();
   }

   @Override
   public boolean isInterface() {
      return get().isInterface();
   }

   @Override
   public boolean isEnum() {
      return get().isEnum();
   }

   @Override
   public boolean isAnnotation() {
      return get().isAnnotation();
   }

   @Override
   public boolean isAnonymous() {
      return get().isAnonymous();
   }

   @Override
   public HxType initialize(final Part... parts) {
      return get().initialize(parts);
   }

   @Override
   public HxType initialize(final Part part) {
      return get().initialize(part);
   }

   @Override
   public HxType getSuperType() {
      return get().getSuperType();
   }

   @Override
   public HxType setSuperType(HxType superType) {
      get().setSuperType(superType);
      return this;
   }

   @Override
   public Collection<HxType> getInterfaces() {
      return get().getInterfaces();
   }

   @Override
   public HxType setInterfaces(Collection<HxType> interfaces) {
      get().setInterfaces(interfaces);
      return this;
   }

   @Override
   public HxType getComponentType() {
      return get().getComponentType();
   }

   @Override
   public Collection<HxType> getDeclaredTypes() {
      return get().getDeclaredTypes();
   }

   @Override
   public HxType setDeclaredTypes(Collection<HxType> declaredTypes) {
      get().setDeclaredTypes(declaredTypes);
      return this;
   }

   @Override
   public HxType addField(HxField field) {
      get().addField(field);
      return this;
   }

   @Override
   public HxType updateField(HxField field) {
      get().updateField(field);
      return this;
   }

   @Override
   public HxType removeField(HxField field) {
      get().removeField(field);
      return this;
   }

   @Override
   public List<HxField> getFields() {
      return get().getFields();
   }

   @Override
   public HxType setFields(List<HxField> fields) {
      get().setFields(fields);
      return this;
   }

   @Override
   public List<HxMethod> getMethods() {
      return get().getMethods();
   }

   @Override
   public HxType setMethods(List<HxMethod> methods) {
      get().setMethods(methods);
      return this;
   }

   @Override
   public List<HxConstructor> getConstructors() {
      return get().getConstructors();
   }

   @Override
   public HxType setConstructors(List<HxConstructor> constructors) {
      get().setConstructors(constructors);
      return this;
   }

   @Override
   public Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
      return get().fields(predicate, recursive);
   }

   @Override
   public Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive) {
      return get().methods(predicate, recursive);
   }

   @Override
   public Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate, boolean recursive) {
      return get().constructors(predicate, recursive);
   }

   @Override
   public Collection<HxType> types(Predicate<HxType> predicate, boolean recursive) {
      return get().types(predicate, recursive);
   }

   @Override
   public Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
      return get().interfaces(predicate, recursive);
   }

   @Override
   public HxType addAnnotation(HxAnnotation annotation) {
      get().addAnnotation(annotation);
      return this;
   }

   @Override
   public HxType setAnnotations(Collection<HxAnnotation> annotations) {
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
   public HxType replaceAnnotation(HxAnnotation annotation) {
      get().replaceAnnotation(annotation);
      return this;
   }

   @Override
   public HxType setAnnotations(HxAnnotation... annotations) {
      get().setAnnotations(annotations);
      return this;
   }

   @Override
   public HxType removeAnnotation(HxAnnotation annotation) {
      get().removeAnnotation(annotation);
      return this;
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
   public HxType setModifiers(HxModifier... modifiers) {
      get().setModifiers(modifiers);
      return this;
   }

   @Override
   public HxType setModifiers(int modifiers) {
      get().setModifiers(modifiers);
      this.modifiers = -1;
      return this;
   }

   @Override
   public int getModifiers() {
      if (modifiers != -1) {
         return modifiers;
      }
      return this.modifiers = get().getModifiers();
   }

   @Override
   public <M extends HxMember> M getDeclaringMember() {
      return get().getDeclaringMember();
   }

   @Override
   public HxType setDeclaringMember(HxMember declaringMember) {
      return get().getDeclaringMember();
   }

   @Override
   public HxField getField(String name) {
      return get().getField(name);
   }

   @Override
   public HxMethod getMethod(String name) {
      return get().getMethod(name);
   }

   @Override
   public Collection<HxMethod> getMethods(String name) {
      return get().getMethods(name);
   }

   @Override
   public HxType toReference() {
      return this;
   }

   //----------------------------------------------------------------------------------------------------------------
}
