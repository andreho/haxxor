package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxGenericType;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxTypeReferenceImpl
    extends HxAbstractType
    implements HxTypeReference {

  private volatile Reference<HxType> reference;

  public HxTypeReferenceImpl(final Haxxor haxxor, final HxType type) {
    super(haxxor, Objects.requireNonNull(type, "Referenced reference can't be null.").getName());
    this.reference = new WeakReference<>(type);
  }

  public HxTypeReferenceImpl(final Haxxor haxxor, final String name) {
    this(haxxor, name, -1);
  }

  public HxTypeReferenceImpl(final Haxxor haxxor, final String name, final int modifiers) {
    super(haxxor, name);
    this.modifiers = modifiers;
  }

  private boolean isAvailable() {
    return isPresent() ||
           getHaxxor().hasType(getName());
  }

  private boolean isPresent() {
    Reference<HxType> reference = this.reference;
    return reference != null && reference.get() != null;
  }

  private boolean hasModifiers() {
    return !isAvailable() &&
           this.modifiers != -1;
  }

  @Override
  public boolean isReference() {
    return true;
  }

  @Override
  public HxType toType() {
    Reference<HxType> reference = this.reference;
    if(reference == null || reference.get() == null) {
      this.reference = reference = new WeakReference<>(getHaxxor().resolve(getName()));
    }
    return reference.get();
  }

  @Override
  public HxType setModifiers(HxModifier modifier, HxModifier... rest) {
    if(isAvailable()) {
      toType().setModifiers(modifier, rest);
      super.setModifiers(-1);
    } else {
      super.setModifiers(modifier, rest);
    }
    return this;
  }

  @Override
  public HxType setModifiers(int modifiers) {
    if(isAvailable()) {
      toType().setModifiers(modifiers);
      super.setModifiers(-1);
    } else {
      super.setModifiers(modifiers);
    }
    return this;
  }

  @Override
  public int getModifiers() {
    if (hasModifiers()) {
      return super.getModifiers();
    }
    return toType().getModifiers();
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
    return isAvailable() && toType().isPrimitive();
  }

  @Override
  public boolean isGeneric() {
    return toType().isGeneric();
  }

  @Override
  public boolean isLocalType() {
    return toType().isLocalType();
  }

  @Override
  public boolean isMemberType() {
    if(!isAvailable() && hasModifiers()) {
      return getSimpleBinaryName() != null && !isLocalOrAnonymousClass();
    }
    return toType().isMemberType();
  }

  @Override
  public boolean isFinal() {
    if (hasModifiers()) {
      return super.isFinal();
    }
    return toType().isFinal();
  }

  @Override
  public boolean isPublic() {
    if (hasModifiers()) {
      return super.isPublic();
    }
    return toType().isPublic();
  }

  @Override
  public boolean isProtected() {
    if (hasModifiers()) {
      return super.isProtected();
    }
    return toType().isProtected();
  }

  @Override
  public boolean isPrivate() {
    if (hasModifiers()) {
      return super.isPrivate();
    }
    return toType().isPrivate();
  }

  @Override
  public boolean isInternal() {
    if (hasModifiers()) {
      return super.isInternal();
    }
    return toType().isInternal();
  }

  @Override
  public boolean isAbstract() {
    if (hasModifiers()) {
      return super.isAbstract();
    }
    return toType().isAbstract();
  }

  @Override
  public boolean isInterface() {
    if (hasModifiers()) {
      return super.isInterface();
    }
    return toType().isInterface();
  }

  @Override
  public boolean isEnum() {
    if (hasModifiers()) {
      return super.isEnum();
    }
    return toType().isEnum();
  }

  @Override
  public boolean isAnnotation() {
    if (hasModifiers()) {
      return super.isAnnotation();
    }
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
  public Optional<HxType> getSuperType() {
    return toType().getSuperType();
  }

  @Override
  public HxType setSuperType(HxType superType) {
    toType().setSuperType(superType);
    return this;
  }

  @Override
  public List<HxType> getInterfaces() {
    return toType().getInterfaces();
  }

  @Override
  public HxType setInterfaces(List<HxType> interfaces) {
    toType().setInterfaces(interfaces);
    return this;
  }

  @Override
  public Optional<HxType> getComponentType() {
    return toType().getComponentType();
  }

  @Override
  public List<HxType> getInnerTypes() {
    return toType().getInnerTypes();
  }

  @Override
  public HxType setInnerTypes(List<HxType> declaredTypes) {
    toType().setInnerTypes(declaredTypes);
    return this;
  }

  @Override
  public HxType addFieldAt(int index,
                           HxField field) {
    toType().addFieldAt(index, field);
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
  public Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
    return toType().fields(predicate, recursive);
  }

  @Override
  public Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive) {
    return toType().methods(predicate, recursive);
  }

  @Override
  public Collection<HxMethod> constructors(Predicate<HxMethod> predicate, boolean recursive) {
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
  public Map<String, HxAnnotation> getAnnotations() {
    return toType().getAnnotations();
  }

  @Override
  public boolean isAnnotationPresent(String type) {
    return toType().isAnnotationPresent(type);
  }

  @Override
  public Optional<HxAnnotation> getAnnotation(String type) {
    return toType().getAnnotation(type);
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
  public HxMember getDeclaringMember() {
    return toType().getDeclaringMember();
  }

  @Override
  public HxType setDeclaringMember(HxMember declaringMember) {
    return toType().setDeclaringMember(declaringMember);
  }

  @Override
  public Optional<String> getGenericSignature() {
    return toType().getGenericSignature();
  }

  @Override
  public HxType setGenericSignature(final String genericSignature) {
    return toType().setGenericSignature(genericSignature);
  }

  @Override
  public Optional<HxGenericType> getGenericType() {
    return toType().getGenericType();
  }

  @Override
  public Optional<HxField> findField(String name) {
    return toType().findField(name);
  }

  @Override
  public Optional<HxMethod> findMethod(String name) {
    return toType().findMethod(name);
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return toType().getMethods(name);
  }

  @Override
  public Appendable toDescriptor(final Appendable builder) {
    if(isPrimitive()) {
      return reference.get().toDescriptor(builder);
    }
    return super.toDescriptor(builder);
  }

  @Override
  public HxTypeReference toReference() {
    return this;
  }
}
