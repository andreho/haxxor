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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static net.andreho.haxxor.Utils.isUninitialized;

/**
 * <br/>Created by andreho on 3/26/16 at 7:21 PM.<br/>
 */
public class HxAbstractType
    extends HxAnnotatedImpl<HxType>
    implements HxType {

  private final Haxxor haxxor;
  private final String name;

  protected HxAbstractType(String name) {
    this.haxxor = null;
    this.name = name;
  }

  public HxAbstractType(Haxxor haxxor, String name) {
    this.haxxor = Objects.requireNonNull(haxxor, "Haxxor is null.");
    this.name = Objects.requireNonNull(name, "Type name is null.");

    if (name.isEmpty()) {
      throw new IllegalArgumentException("Type name is empty.");
    }
  }

  @Override
  public Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Version getVersion() {
    return Version.V1_8;
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
      return this;
    }
    return setSuperType(getHaxxor().reference(superType));
  }

  @Override
  public Collection<HxType> getInterfaces() {
    return Collections.emptySet();
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
    switch (part) {
      case DEFAULTS: {
        initialize(Part.CONSTRUCTORS);
      }
      break;
      case ANNOTATIONS: {
        if (isUninitialized(getAnnotations())) {
          setAnnotations(new LinkedHashSet<>(0));
          return this;
        }
      }
      break;
      case CONSTRUCTORS: {
        if (isUninitialized(getConstructors())) {
          return setConstructors(new ArrayList<>());
        }
      }
      break;
      case FIELDS: {
        if (isUninitialized(getFields())) {
          return setFields(new ArrayList<>());
        }
      }
      break;
      case INTERFACES: {
        if (isUninitialized(getInterfaces())) {
          return setInterfaces(new LinkedHashSet<>(0));
        }
      }
      break;
      case METHODS: {
        if (isUninitialized(getMethods())) {
          return setMethods(new ArrayList<>());
        }
      }
      break;
      case DECLARED_TYPES: {
        if (isUninitialized(getDeclaredTypes())) {
          return setDeclaredTypes(new LinkedHashSet<>(0));
        }
      }
      break;
      default:
        throw new IllegalStateException("Not reachable.");
    }

    return this;
  }

  @Override
  public String getJavaName() {
    if (isArray()) {
      int dim = getDimension();

      HxType component = getComponentType();

      while (component.getComponentType() != null) {
        component = component.getComponentType();
      }

      final StringBuilder builder = new StringBuilder(component.getJavaName());

      while (dim-- > 0) {
        builder.append("[]");
      }

      return builder.toString();
    } else if (isPrimitive()) {
      switch (getName()) {
        case "Z":
          return "boolean";
        case "B":
          return "byte";
        case "C":
          return "char";
        case "S":
          return "short";
        case "I":
          return "int";
        case "F":
          return "float";
        case "J":
          return "long";
        case "D":
          return "double";
        case "V":
          return "void";
        default:
          throw new IllegalStateException("Type: " + getName());
      }
    }

    return getName().replace('/', '.');
  }

  @Override
  public int getSlotsCount() {
    if (isPrimitive() &&
        ("J".equals(getName()) || "D".equals(getName()))) {
      return 2;
    }
    return 1;
  }

  @Override
  public boolean isReference() {
    return false;
  }

  @Override
  public HxType setInterfaces(Collection<HxType> interfaces) {
    //NO OP
    return this;
  }

  @Override
  public Collection<HxType> getDeclaredTypes() {
    return Collections.emptySet();
  }

  @Override
  public HxType setDeclaredTypes(Collection<HxType> declaredTypes) {
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
  public HxField getField(String name) {
    //NO OP
    return null;
  }

  @Override
  public boolean hasField(String name) {
    return getField(name) != null;
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
  public Collection<HxMethod> getMethods(String name) {
    return Collections.emptySet();
  }

  @Override
  public HxType addMethod(HxMethod method) {
    initialize(Part.METHODS);

    if (!isUninitialized(getMethods())) {
      if (method.getDeclaringMember() != null || !getMethods().add(method)) {
        throw new IllegalStateException("Ambiguous method: " + method);
      }

      if (!getMethods(method.getName()).add(method)) {
        throw new IllegalStateException("Ambiguous method: " + method);
      }

      method.setDeclaringMember(this);
    }
    return this;
  }

  @Override
  public HxMethod getMethod(String name) {
    //NO OP
    return null;
  }

  @Override
  public HxMethod getMethodDirectly(String name, String desc) {
    final Iterable<HxMethod> methods = getMethods(name);

    for (HxMethod method : methods) {
      if (desc.equals(method.toDescriptor())) {
        return method;
      }
    }
    return null;
  }

  @Override
  public HxMethod getMethod(String name, String returnType, String... parameters) {
    final Iterable<HxMethod> methods = getMethods(name);

    loop:
    for (HxMethod method : methods) {
      if (parameters.length != method.getArity()) {
        continue;
      }

      for (int i = 0, arity = method.getArity(); i < arity; i++) {
        HxType type = method.getParameterTypeAt(i);

        if (!type.getName().equals(parameters[i])) {
          continue loop;
        }
      }

      if (returnType == null || method.getReturnType().is(returnType)) {
        return method;
      }
    }
    return null;
  }

  @Override
  public HxMethod getMethod(final String name, final HxType returnType, final HxType... parameters) {
    final Iterable<HxMethod> methods = getMethods(name);

    loop:
    for (HxMethod method : methods) {
      if (parameters.length != method.getArity()) {
        continue;
      }

      for (int i = 0, arity = method.getArity(); i < arity; i++) {
        HxType type = method.getParameterTypeAt(i);

        if (!type.equals(parameters[i])) {
          continue loop;
        }
      }
      if (returnType == null || returnType.equals(method.getReturnType())) {
        return method;
      }
    }
    return null;
  }

  @Override
  public HxMethod getMethod(final String name, final HxType returnType, final List<HxType> parameters) {
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

      if (returnType == null || returnType.equals(method.getReturnType())) {
        return method;
      }
    }
    return null;
  }

  @Override
  public boolean hasMethod(final String name, final String returnType, final String... parameters) {
    return getMethod(name, returnType, parameters) != null;
  }

  @Override
  public boolean hasMethod(final String name, final HxType returnType, final HxType... parameters) {
    return getMethod(name, returnType, parameters) != null;
  }

  @Override
  public boolean hasMethod(final String name, final HxType returnType, final List<HxType> signature) {
    return getMethod(name, returnType, signature) != null;
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

    if (equals(constructor.getDeclaringMember())) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    } else if (constructor.getDeclaringMember() != null) {
      constructor = constructor.clone();
    }

    if (constructor.getDeclaringMember() != null ||
        getConstructorWithParameters(constructor.getParameters()) != null ||
        !getConstructors().add(constructor)) {
      throw new IllegalStateException("Ambiguous constructor: " + constructor);
    }

    constructor.setDeclaringMember(this);
    return this;
  }

  @Override
  public HxConstructor getConstructorWithParameters(final List<HxParameter<HxConstructor>> signature) {
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
      return constructor;
    }
    return null;
  }

  @Override
  public HxConstructor getConstructor(final List<HxType> signature) {
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
      return constructor;
    }
    return null;
  }

  @Override
  public HxConstructor getConstructor(HxType... signature) {
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
      return constructor;
    }
    return null;
  }

  @Override
  public HxConstructor getConstructor(String... signature) {
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
      return constructor;
    }
    return null;
  }

  @Override
  public boolean hasConstructor(final String... signature) {
    return getConstructor(signature) != null;
  }

  @Override
  public boolean hasConstructor(final HxType... signature) {
    return getConstructor(signature) != null;
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
  public boolean is(String className) {
    className = getHaxxor().getTypeNamingStrategy()
                           .toTypeName(className);
    return getName().equals(className);
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

  @Override
  public String getSimpleBinaryName() {
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
    if (isArray()) {
      int dim = getDimension();

      StringBuilder builder = new StringBuilder(getComponentType().getSimpleName());

      while (dim-- > 0) {
        builder.append("[]");
      }

      return builder.toString();
    }

    String simpleName = getSimpleBinaryName();

    if (simpleName == null) {
      // top level class
      simpleName = getName();

      int idx = simpleName.lastIndexOf('/');

      if (idx > -1) {
        simpleName.substring(idx + 1);
      }

      idx = simpleName.lastIndexOf('$');

      if (idx > -1) {
        simpleName = simpleName.substring(idx + 1);
      }

      return simpleName;
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
  public String getPackage() {
    int index = getName().lastIndexOf('/');
    return index > -1 ? getName().substring(0, index) : "";
  }

  @Override
  public boolean isArray() {
    return getDimension() > 0;
  }

  @Override
  public HxType getComponentType() {
    if (isArray()) {
      return getHaxxor().reference(getName().substring(1));
    }
    return null;
  }

  @Override
  public int getDimension() {
    int idx = 0;
    int dims = 0;

    while (getName().charAt(idx++) == '[') {
      dims++;
    }

    return dims;
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
    return getHaxxor().reference(getName());
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
             .append(type.getName()
                         .replace('.', '/'))
             .append(';');
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return builder;
  }

  @Override
  public String toDescriptor() {
    return toDescriptor(new StringBuilder()).toString();
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
