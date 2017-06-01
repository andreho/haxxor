package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxTypeReferenceImpl
    extends HxAbstractType
    implements HxTypeReference {

  public HxTypeReferenceImpl(Haxxor haxxor, String name) {
    this(haxxor, name, -1);
  }

  public HxTypeReferenceImpl(Haxxor haxxor, String name, int modifiers) {
    super(haxxor, name);
    this.modifiers = modifiers;
  }

  @Override
  public HxType toType() {
    return getHaxxor().resolve(getName());
  }

  @Override
  public Version getVersion() {
    return toType().getVersion();
  }

  @Override
  public HxType setVersion(Version version) {
    toType().setVersion(version);
    return this;
  }

  @Override
  public boolean isPrimitive() {
    return toType().isPrimitive();
  }

  @Override
  public boolean isArray() {
    return toType().isArray();
  }

  @Override
  public boolean isGeneric() {
    return toType().isGeneric();
  }

  @Override
  public int getDimension() {
    return toType().getDimension();
  }

  @Override
  public boolean isLocalType() {
    return toType().isLocalType();
  }

  @Override
  public boolean isMemberType() {
    return toType().isMemberType();
  }

  @Override
  public boolean isFinal() {
    return toType().isFinal();
  }

  @Override
  public boolean isPublic() {
    return toType().isPublic();
  }

  @Override
  public boolean isProtected() {
    return toType().isProtected();
  }

  @Override
  public boolean isPrivate() {
    return toType().isPrivate();
  }

  @Override
  public boolean isInternal() {
    return toType().isInternal();
  }

  @Override
  public boolean isAbstract() {
    return toType().isAbstract();
  }

  @Override
  public boolean isInterface() {
    return toType().isInterface();
  }

  @Override
  public boolean isEnum() {
    return toType().isEnum();
  }

  @Override
  public boolean isAnnotation() {
    return toType().isAnnotation();
  }

  @Override
  public boolean isAnonymous() {
    return toType().isAnonymous();
  }

  @Override
  public HxType initialize(final Part... parts) {
    return toType().initialize(parts);
  }

  @Override
  public HxType initialize(final Part part) {
    return toType().initialize(part);
  }

  @Override
  public HxType getSuperType() {
    return toType().getSuperType();
  }

  @Override
  public HxType setSuperType(HxType superType) {
    toType().setSuperType(superType);
    return this;
  }

  @Override
  public Collection<HxType> getInterfaces() {
    return toType().getInterfaces();
  }

  @Override
  public HxType setInterfaces(Collection<HxType> interfaces) {
    toType().setInterfaces(interfaces);
    return this;
  }

  @Override
  public HxType getComponentType() {
    return toType().getComponentType();
  }

  @Override
  public Collection<HxType> getDeclaredTypes() {
    return toType().getDeclaredTypes();
  }

  @Override
  public HxType setDeclaredTypes(Collection<HxType> declaredTypes) {
    toType().setDeclaredTypes(declaredTypes);
    return this;
  }

  @Override
  public HxType addField(HxField field) {
    toType().addField(field);
    return this;
  }

  @Override
  public HxType updateField(HxField field) {
    toType().updateField(field);
    return this;
  }

  @Override
  public HxType removeField(HxField field) {
    toType().removeField(field);
    return this;
  }

  @Override
  public List<HxField> getFields() {
    return toType().getFields();
  }

  @Override
  public HxType setFields(List<HxField> fields) {
    toType().setFields(fields);
    return this;
  }

  @Override
  public List<HxMethod> getMethods() {
    return toType().getMethods();
  }

  @Override
  public HxType setMethods(List<HxMethod> methods) {
    toType().setMethods(methods);
    return this;
  }

  @Override
  public List<HxConstructor> getConstructors() {
    return toType().getConstructors();
  }

  @Override
  public HxType setConstructors(List<HxConstructor> constructors) {
    toType().setConstructors(constructors);
    return this;
  }

  @Override
  public Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
    return toType().fields(predicate, recursive);
  }

  @Override
  public Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive) {
    return toType().methods(predicate, recursive);
  }

  @Override
  public Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate, boolean recursive) {
    return toType().constructors(predicate, recursive);
  }

  @Override
  public Collection<HxType> types(Predicate<HxType> predicate, boolean recursive) {
    return toType().types(predicate, recursive);
  }

  @Override
  public Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
    return toType().interfaces(predicate, recursive);
  }

  @Override
  public HxType addAnnotation(HxAnnotation annotation) {
    toType().addAnnotation(annotation);
    return this;
  }

  @Override
  public HxType setAnnotations(Collection<HxAnnotation> annotations) {
    toType().setAnnotations(annotations);
    return this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return toType().getSuperAnnotated();
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return toType().getAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(String type) {
    return toType().isAnnotationPresent(type);
  }

  @Override
  public HxAnnotation getAnnotation(String type) {
    return toType().getAnnotation(type);
  }

  @Override
  public HxType replaceAnnotation(HxAnnotation annotation) {
    toType().replaceAnnotation(annotation);
    return this;
  }

  @Override
  public HxType setAnnotations(HxAnnotation... annotations) {
    toType().setAnnotations(annotations);
    return this;
  }

  @Override
  public HxType removeAnnotation(HxAnnotation annotation) {
    toType().removeAnnotation(annotation);
    return this;
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(String type) {
    return toType().getAnnotationsByType(type);
  }

  @Override
  public Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive) {
    return toType().annotations(predicate, recursive);
  }

  @Override
  public HxType setModifiers(HxModifier... modifiers) {
    toType().setModifiers(modifiers);
    return this;
  }

  @Override
  public HxType setModifiers(int modifiers) {
    toType().setModifiers(modifiers);
    this.modifiers = -1;
    return this;
  }

  @Override
  public int getModifiers() {
    if (modifiers != -1) {
      return modifiers;
    }
    return this.modifiers = toType().getModifiers();
  }

  @Override
  public <M extends HxMember> M getDeclaringMember() {
    return toType().getDeclaringMember();
  }

  @Override
  public HxType setDeclaringMember(HxMember declaringMember) {
    return toType().getDeclaringMember();
  }

  @Override
  public HxField getField(String name) {
    return toType().getField(name);
  }

  @Override
  public HxMethod getMethod(String name) {
    return toType().getMethod(name);
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return toType().getMethods(name);
  }

  @Override
  public HxTypeReference toReference() {
    return this;
  }
}
