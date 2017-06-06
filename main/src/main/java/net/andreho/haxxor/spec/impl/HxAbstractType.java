package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxGeneric;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxParameterizable;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <br/>Created by andreho on 3/26/16 at 7:21 PM.<br/>
 */
public class HxAbstractType
    extends HxAnnotatedImpl<HxType>
    implements HxType {

  public static final char JAVA_PACKAGE_SEPARATOR_CHAR = '.';
  public static final char INTERNAL_PACKAGE_SEPARATOR_CHAR = '/';

  private final Haxxor haxxor;
  private final String name;

  public HxAbstractType(Haxxor haxxor, String name) {
    this.haxxor = Objects.requireNonNull(haxxor, "Associated haxxor instance can't be null.");
    this.name = Objects.requireNonNull(name, "Given internal classname can't be null.");

    if (name.isEmpty()) {
      throw new IllegalArgumentException("Given internal classname can't be empty.");
    }
  }

  @Override
  public Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  public Version getVersion() {
    return Version.V1_8;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public HxType setVersion(Version version) {
    //NO OP
    return this;
  }

  @Override
  public HxType getSuperType() {
    return null;
  }

  @Override
  public HxType setSuperType(HxType superType) {
    //NO OP
    return this;
  }

  @Override
  public HxType setSuperType(String superType) {
    if (superType == null) {
      if("java.lang.Object".equals(getName())) {
        setSuperType((HxType) null);
        return this;
      }
      throw new IllegalArgumentException("Super type can't be null.");
    }
    return setSuperType(getHaxxor().reference(superType));
  }

  @Override
  public List<HxType> getInterfaces() {
    return Collections.emptyList();
  }

  @Override
  public HxType initialize(Part... parts) {
    for (Part part : parts) {
      initialize(part);
    }
    return this;
  }

  @Override
  public HxType initialize(Part part) {
    return this;
  }

  @Override
  public int getSlotsCount() {
    if (isPrimitive() && ("long".equals(getName()) || "double".equals(getName()))) {
      return 2;
    }
    return 1;
  }

  @Override
  public boolean isReference() {
    return this instanceof HxTypeReference;
  }

  @Override
  public HxType setInterfaces(List<HxType> interfaces) {
    //NO OP
    return this;
  }

  @Override
  public List<HxType> getDeclaredTypes() {
    return Collections.emptyList();
  }

  @Override
  public HxType setDeclaredTypes(List<HxType> declaredTypes) {
    //NO OP
    return this;
  }

  @Override
  public HxType addField(HxField field) {
    //NO OP
    return this;
  }

  @Override
  public HxType updateField(HxField field) {
    //NO OP
    return this;
  }

  @Override
  public HxType removeField(HxField field) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxField> getField(String name) {
    //NO OP
    return Optional.empty();
  }

  @Override
  public boolean hasField(String name) {
    return getField(name).isPresent();
  }

  @Override
  public List<HxField> getFields() {
    return Collections.emptyList();
  }

  @Override
  public HxType setFields(List<HxField> fields) {
    //NO OP
    return this;
  }

  @Override
  public List<HxMethod> getMethods() {
    return Collections.emptyList();
  }

  @Override
  public HxType setMethods(List<HxMethod> methods) {
    //NO OP
    return this;
  }

  @Override
  public HxType removeMethod(final HxMethod method) {
    //NO OP
    return this;
  }

  @Override
  public HxType addMethod(HxMethod method) {
    initialize(Part.METHODS);

    if (this == method.getDeclaringMember()) {
      throw new IllegalStateException("Method was already added: " + method);
    } else if (method.getDeclaringMember() != null) {
      method = method.clone();
    }
    if (method.getDeclaringMember() != null) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }
    if (!getMethods(method.getName()).add(method)) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }
    if (!getMethods().add(method)) {
      throw new IllegalStateException("Ambiguous method: " + method);
    }

    method.setDeclaringMember(this);
    return this;
  }

  @Override
  public Optional<HxMethod> getMethod(String name) {
    //NO OP
    return Optional.empty();
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return Collections.emptySet();
  }

  @Override
  public Optional<HxMethod> getMethodDirectly(String name, String desc) {
    final Iterable<HxMethod> methods = getMethods(name);
    for (HxMethod method : methods) {
      if (desc.equals(method.toDescriptor())) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> getMethod(String name, String returnType, String... parameters) {
    return getMethod(name, getHaxxor().reference(returnType), getHaxxor().referencesAsArray(parameters));
  }

  @Override
  public Optional<HxMethod> getMethod(final String name, final HxType returnType, final HxType... parameters) {
    return getMethod(name, returnType, Arrays.asList(parameters));
  }

  @Override
  public Optional<HxMethod> getMethod(final String name, final HxType returnType, final List<HxType> parameters) {
    final Iterable<HxMethod> methods = getMethods(name);

    loop:
    for (HxMethod method : methods) {
      if (parameters.size() != method.getArity()) {
        continue;
      }

      for (int i = 0, arity = method.getArity(); i < arity; i++) {
        HxType type = method.getParameterTypeAt(i);

        if (!type.equals(parameters.get(i))) {
          continue loop;
        }
      }

      if (returnType == null ||
          returnType.equals(method.getReturnType())) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  @Override
  public boolean hasMethod(final String name, final String returnType, final String... parameters) {
    return getMethod(name, returnType, parameters).isPresent();
  }

  @Override
  public boolean hasMethod(final String name, final HxType returnType, final HxType... parameters) {
    return getMethod(name, returnType, parameters).isPresent();
  }

  @Override
  public boolean hasMethod(final String name, final HxType returnType, final List<HxType> signature) {
    return getMethod(name, returnType, signature).isPresent();
  }

  @Override
  public List<HxConstructor> getConstructors() {
    return Collections.emptyList();
  }

  @Override
  public HxType setConstructors(List<HxConstructor> constructors) {
    //NO OP
    return this;
  }

  @Override
  public HxType addConstructor(HxConstructor constructor) {
    initialize(Part.CONSTRUCTORS);

    if (this == constructor.getDeclaringMember()) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    } else if (constructor.getDeclaringMember() != null) {
      constructor = constructor.clone();
    }

    if (constructor.getDeclaringMember() != null ||
        getConstructorWithParameters(constructor.getParameters()).isPresent() ||
        !getConstructors().add(constructor)) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    }

    constructor.setDeclaringMember(this);
    return this;
  }

  @Override
  public Optional<HxConstructor> getConstructorWithParameters(final List<HxParameter<HxConstructor>> signature) {
    loop:
    for (HxConstructor constructor : getConstructors()) {
      if (signature.size() != constructor.getArity()) {
        continue;
      }
      for (int i = 0, arity = constructor.getArity(); i < arity; i++) {
        HxType type = constructor.getParameterTypeAt(i);

        if (!type.equals(signature.get(i)
                                  .getType())) {
          continue loop;
        }
      }
      return Optional.of(constructor);
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxConstructor> getConstructor(final List<HxType> signature) {
    loop:
    for (HxConstructor constructor : getConstructors()) {
      if (signature.size() != constructor.getArity()) {
        continue;
      }

      for (int i = 0, arity = constructor.getArity(); i < arity; i++) {
        HxType type = constructor.getParameterTypeAt(i);

        if (!type.equals(signature.get(i))) {
          continue loop;
        }
      }
      return Optional.of(constructor);
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxConstructor> getConstructor(HxType... signature) {
    loop:
    for (HxConstructor constructor : getConstructors()) {
      if (signature.length != constructor.getArity()) {
        continue;
      }

      for (int i = 0, arity = constructor.getArity(); i < arity; i++) {
        HxType type = constructor.getParameterTypeAt(i);

        if (!type.equals(signature[i])) {
          continue loop;
        }
      }
      return Optional.of(constructor);
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxConstructor> getConstructor(String... signature) {
    loop:
    for (HxConstructor constructor : getConstructors()) {
      if (signature.length != constructor.getArity()) {
        continue;
      }

      for (int i = 0, arity = constructor.getArity(); i < arity; i++) {
        HxType type = constructor.getParameterTypeAt(i);

        if (!type.getName()
                 .equals(signature[i])) {
          continue loop;
        }
      }
      return Optional.of(constructor);
    }
    return Optional.empty();
  }

  @Override
  public boolean hasConstructor(final String... signature) {
    return getConstructor(signature).isPresent();
  }

  @Override
  public boolean hasConstructor(final HxType... signature) {
    return getConstructor(signature).isPresent();
  }

  @Override
  public boolean isGeneric() {
    return getGenericSignature() == null || !getGenericSignature().isEmpty();
  }

  @Override
  public String getGenericSignature() {
    return "";
  }

  @Override
  public HxType setGenericSignature(String genericSignature) {
    return this;
  }

  @Override
  public HxGeneric getGenericSuperType() {
    return getSuperType();
  }

  @Override
  public List<HxGeneric> getGenericInterfaces() {
    return Collections.emptyList();
  }

  @Override
  public boolean hasName(String className) {
    return getName().equals(getHaxxor().toJavaClassName(className));
  }

  @Override
  public boolean isTypeOf(HxType otherType) {
    return otherType.isAssignableFrom(this);
  }

  @Override
  public boolean isAssignableFrom(final HxType otherType) {
    if(otherType == null) {
      return false;
    } else if (this.equals(otherType)) {
      return true;
    } else if (this.isPrimitive()) {
      return false;
    }

    if (isArray()) {
      if (!otherType.isArray()) {
        return false;
      }

      return this.getDimension() == otherType.getDimension() &&
             this.getComponentType()
                 .isAssignableFrom(otherType.getComponentType());
    }

    HxType current = otherType;

    while (current != null) {
      if (this.equals(current)) {
        return true;
      }

      if (otherType.isInterface()) {
        final Collection<HxType> interfaces = current.getInterfaces();

        if (interfaces.contains(this)) {
          return true;
        }

        for (HxType ifc : interfaces) {
          if (this.isAssignableFrom(ifc)) {
            return true;
          }
        }
      }

      current = current.getSuperType();
    }

    return false;
  }

  @Override
  public Collection<HxField> fields() {
    return fields((f) -> true);
  }

  @Override
  public Collection<HxField> fields(Predicate<HxField> predicate) {
    return fields(predicate, false);
  }

  @Override
  public Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive) {
    //NO OP
    return Collections.emptySet();
  }

  @Override
  public Collection<HxMethod> methods() {
    return methods((m) -> true);
  }

  @Override
  public Collection<HxMethod> methods(Predicate<HxMethod> predicate) {
    return methods(predicate, false);
  }

  @Override
  public Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive) {
    //NO OP
    return Collections.emptySet();
  }

  @Override
  public Collection<HxConstructor> constructors() {
    return constructors((c) -> true);
  }

  @Override
  public Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate) {
    return constructors(predicate, false);
  }

  @Override
  public Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate, boolean recursive) {
    //NO OP
    return Collections.emptySet();
  }

  @Override
  public Collection<HxType> types() {
    return types((t) -> true);
  }

  @Override
  public Collection<HxType> types(Predicate<HxType> predicate) {
    return types(predicate, false);
  }

  @Override
  public Collection<HxType> types(Predicate<HxType> predicate, boolean recursive) {
    //NO OP
    return Collections.emptySet();
  }

  @Override
  public Collection<HxType> interfaces() {
    return interfaces((i) -> true);
  }

  @Override
  public Collection<HxType> interfaces(Predicate<HxType> predicate) {
    return interfaces(predicate, false);
  }

  @Override
  public Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive) {
    //NO OP
    return Collections.emptySet();
  }

  @Override
  public HxType getEnclosingType() {
    if (getEnclosingMethod() != null) {
      return getEnclosingMethod().getDeclaringMember();
    } else if (getEnclosingConstructor() != null) {
      return getEnclosingConstructor().getDeclaringMember();
    }
    return getDeclaringMember();
  }

  @Override
  public HxMethod getEnclosingMethod() {
    if (getDeclaringMember() instanceof HxMethod) {
      return getDeclaringMember();
    }
    return null;
  }

  @Override
  public HxConstructor getEnclosingConstructor() {
    if (getDeclaringMember() instanceof HxConstructor) {
      return getDeclaringMember();
    }
    return null;
  }

  private String getSimpleBinaryName() {
    /* Code was copied from original java.lang.Class */
    HxType enclosingType = getEnclosingType();
    if (enclosingType == null) {
      // top level class
      return null;
    }
    // Otherwise, strip the enclosing class' name
    try {
      return getName().substring(enclosingType.getName()
                                              .length());
    } catch (IndexOutOfBoundsException ex) {
      throw new InternalError("Malformed class name: " + getName(), ex);
    }
  }

  @Override
  public String getSimpleName() {
    /* Code was copied from original java.lang.Class */
    if (isArray()) {
      return getComponentType().getSimpleName() + "[]";
    }

    String simpleName = getSimpleBinaryName();
    if (simpleName == null) {
      // top level class
      simpleName = getName();
      return simpleName.substring(simpleName.lastIndexOf(".")+1); // strip the package name
    }

    // Remove leading "\$[0-9]*" from the name
    int length = simpleName.length();
    if (length < 1 || simpleName.charAt(0) != '$') {
      throw new InternalError("Malformed class name");
    }
    int index = 1;
    while (index < length &&
           simpleName.charAt(index) >= '0' &&
           simpleName.charAt(index) <= '9') {
      index++;
    }
    // Eventually, this is the empty string if this is an anonymous class
    return simpleName.substring(index);
  }

  @Override
  public String getPackageName() {
    int index = getName().lastIndexOf(JAVA_PACKAGE_SEPARATOR_CHAR);
    return index < 0 ? "" : getName().substring(0, index);
  }

  @Override
  public boolean isArray() {
    return getDimension() > 0;
  }

  @Override
  public HxType getComponentType() {
    if (isArray()) {
      String name = getName();
      String componentType = name.substring(0, name.length() - 2);
      return getHaxxor().reference(componentType);
    }
    return null;
  }

  @Override
  public int getDimension() {
    final String name = getName();
    final int len = name.length();

    int dim = 0;
    int pos = len;

    while (pos >= 2 &&
           name.charAt(pos - 2) == '[' &&
           name.charAt(pos - 1) == ']') {
      dim++;
      pos-=2;
    }
    return dim;
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isLocalType() {
    return getDeclaringMember() instanceof HxMethod &&
           !isAnonymous();
  }

  @Override
  public boolean isMemberType() {
    return getSimpleBinaryName() != null &&
           getDeclaringMember() instanceof HxParameterizable;
  }

  @Override
  public boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  @Override
  public boolean isPublic() {
    return hasModifiers(Modifiers.PUBLIC);
  }

  @Override
  public boolean isProtected() {
    return hasModifiers(Modifiers.PROTECTED);
  }

  @Override
  public boolean isPrivate() {
    return hasModifiers(Modifiers.PRIVATE);
  }

  @Override
  public boolean isInternal() {
    return !isPublic() &&
           !isProtected() &&
           !isPrivate();
  }

  @Override
  public boolean isAbstract() {
    return hasModifiers(Modifiers.ABSTRACT);
  }

  @Override
  public boolean isInterface() {
    return hasModifiers(Modifiers.INTERFACE);
  }

  @Override
  public boolean isEnum() {
    return hasModifiers(Modifiers.ENUM);
  }

  @Override
  public boolean isAnnotation() {
    return hasModifiers(Modifiers.ANNOTATION);
  }

  @Override
  public boolean isAnonymous() {
    return getSimpleName().isEmpty();
  }

  @Override
  public HxTypeReference toReference() {
    if(isReference()) {
      return (HxTypeReference) this;
    }
    return getHaxxor().createReference(this);
  }

  @Override
  public Appendable toDescriptor(Appendable builder) {
    HxType type = this;

    try {
      while (type.isArray()) {
        builder.append('[');

        type = type.getComponentType();
      }

      if (type.isPrimitive()) {
        return type.toDescriptor(builder);
      }

      builder.append('L')
             .append(type.getName().replace(JAVA_PACKAGE_SEPARATOR_CHAR, '/'))
             .append(';');
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return builder;
  }

  @Override
  public String toDescriptor() {
    return toDescriptor(new StringBuilder(getName().length() + 2)).toString();
  }

  @Override
  public String toInternalName() {
    return getHaxxor().getInternalClassNameProvider().toInternalClassName(getName());
  }

  @Override
  public byte[] toByteArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Class<?> loadClass() {
    return null;
  }

  @Override
  public Class<?> loadClass(final ClassLoader classLoader) {
    return null;
  }

  @Override
  public HxType setModifiers(HxModifier... modifiers) {
    super.setModifiers(modifiers);
    return this;
  }

  @Override
  public HxType setModifiers(int modifiers) {
    super.setModifiers(modifiers);
    return this;
  }

  @Override
  public HxType setAnnotations(Collection<HxAnnotation> annotations) {
    super.setAnnotations(annotations);
    return this;
  }

  @Override
  public HxType addAnnotation(HxAnnotation annotation) {
    super.addAnnotation(annotation);
    return this;
  }

  @Override
  public HxType replaceAnnotation(HxAnnotation annotation) {
    super.replaceAnnotation(annotation);
    return this;
  }

  @Override
  public HxType setAnnotations(HxAnnotation... annotations) {
    super.setAnnotations(annotations);
    return this;
  }

  @Override
  public HxType removeAnnotation(HxAnnotation annotation) {
    super.removeAnnotation(annotation);
    return this;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof HxType)) {
      return false;
    }
    return Objects.equals(getName(), ((HxType) obj).getName());
  }

  @Override
  public String toString() {
    return getName();
  }
}
