package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstants;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxExecutable;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxGenericType;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxModifier;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

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
  public Optional<HxType> getSuperType() {
    return Optional.empty();
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
  public HxType addFieldAt(int index,
                           HxField field) {
    //NO OP
    return this;
  }

  @Override
  public HxType removeField(HxField field) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxField> findField(String name) {
    //NO OP
    return Optional.empty();
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
  public HxType addMethodAt(int index,
                            HxMethod method) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxMethod> findMethod(String name) {
    //NO OP
    return Optional.empty();
  }

  @Override
  public Collection<HxMethod> getMethods(String name) {
    return Collections.emptySet();
  }

  @Override
  public Optional<HxMethod> findMethodDirectly(String name, String descriptor) {
    for (HxMethod method : getMethods(name)) {
      if (method.hasDescriptor(descriptor)) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxMethod> findMethod(final Optional<HxType> returnType,
                                       final String name,
                                       final List<HxType> parameters) {
    final Iterable<HxMethod> methods = getMethods(name);

    loop:
    for (HxMethod method : methods) {
      if (parameters.size() != method.getParametersCount()) {
        continue;
      }

      for (int i = 0, arity = method.getParametersCount(); i < arity; i++) {
        HxType type = method.getParameterTypeAt(i);

        if (!type.equals(parameters.get(i))) {
          continue loop;
        }
      }

      if (returnType.isPresent() &&
          Objects.equals(returnType.get(), method.getReturnType())) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
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
  public HxType removeConstructor(final HxConstructor constructor) {
    //NO OP
    return this;
  }

  @Override
  public HxType addConstructorAt(int index, HxConstructor constructor) {
    //NO OP
    return this;
  }

  @Override
  public Optional<HxConstructor> findConstructorDirectly(final String descriptor) {
    for (HxConstructor constructor : getConstructors()) {
      if (constructor.hasDescriptor(descriptor)) {
        return Optional.of(constructor);
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxConstructor> findConstructor(final List<HxType> signature) {
    loop:
    for (HxConstructor constructor : getConstructors()) {
      if (signature.size() != constructor.getParametersCount()) {
        continue;
      }

      for (int i = 0, arity = constructor.getParametersCount(); i < arity; i++) {
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
  public Optional<HxConstructor> findConstructor(HxType... signature) {
    return findConstructor(Arrays.asList(signature));
  }

  @Override
  public Optional<HxConstructor> findConstructor(String... signature) {
    return findConstructor(getHaxxor().referencesAsArray(signature));
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
  public Optional<HxGenericType> getGenericType() {
    return Optional.empty();
  }

  @Override
  public boolean isAssignableFrom(final HxType otherType) {
    if(otherType == null) {
      return false;
    } else if (equals(otherType)) {
      return true;
    } else if (isPrimitive()) {
      return false;
    } else if (isArray()) {
      if (!otherType.isArray()) {
        return false;
      }
      return this.getDimension() == otherType.getDimension() &&
             this.getComponentType().get()
                 .isAssignableFrom(otherType.getComponentType().get());
    }

    HxType current = otherType; //current is not NULL at this moment

    if(isInterface()) {
      do {
        //search only for interfaces of current type (independent whether interface or not)
        for(HxType itf : current.getInterfaces()) {
          if(isAssignableFrom(itf)) {
            return true;
          }
        }
        if(!current.hasSuperType()) {
          break;
        }
        current = current.getSuperType().get();
      } while (true);
    } else {
      do {
        if (equals(current)) {
          return true;
        }
        if(!current.hasSuperType()) {
          break;
        }
        current = current.getSuperType().get();
      } while (true);
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
  public Optional<HxType> getEnclosingType() {
    if (getEnclosingMethod().isPresent()) {
      return Optional.of(getEnclosingMethod().get().getDeclaringMember());
    } else if (getEnclosingConstructor().isPresent()) {
      return Optional.of(getEnclosingConstructor().get().getDeclaringMember());
    }
    return Optional.ofNullable(getDeclaringMember());
  }

  @Override
  public Optional<HxMethod> getEnclosingMethod() {
    if (getDeclaringMember() instanceof HxMethod) {
      return Optional.of(getDeclaringMember());
    }
    return Optional.empty();
  }

  @Override
  public Optional<HxConstructor> getEnclosingConstructor() {
    if (getDeclaringMember() instanceof HxConstructor) {
      return Optional.of(getDeclaringMember());
    }
    return Optional.empty();
  }

  private String getSimpleBinaryName() {
    /* Code was copied from original java.lang.Class */
    Optional<HxType> enclosingType = getEnclosingType();
    if (!enclosingType.isPresent()) {
      // top level class
      return null;
    }
    // Otherwise, strip the enclosing class' name
    try {
      return getName().substring(enclosingType.get()
                                              .getName()
                                              .length());
    } catch (IndexOutOfBoundsException ex) {
      throw new InternalError("Malformed class name: " + getName(), ex);
    }
  }

  @Override
  public String getSimpleName() {
    /* Code was copied from original java.lang.Class */
    if (isArray()) {
      return getComponentType().get().getSimpleName() + "[]";
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
  public Optional<HxType> getComponentType() {
    if (isArray()) {
      String name = getName();
      String componentType = name.substring(0, name.length() - 2);
      HxTypeReference reference = getHaxxor().reference(componentType);
      return Optional.of(reference);
    }
    return Optional.empty();
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
           getDeclaringMember() instanceof HxExecutable;
  }

  @Override
  public HxTypeReference toReference() {
    if(isReference()) {
      return (HxTypeReference) this;
    }
    return getHaxxor().createReference(this);
  }

  @Override
  public byte[] toByteArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Class<?> loadClass(final ClassLoader classLoader)
  throws ClassNotFoundException {
    String name = getName();
    if(isArray()) {
      name = toDescriptor()
          .replace(HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR, HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR);
    }
    return Class.forName(name, true, classLoader);
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
