package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static net.andreho.haxxor.Utils.toClassNames;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public interface HxType
    extends HxAnnotated<HxType>,
            HxMember<HxType>,
            HxOwned<HxType>,
            HxGeneric<HxType>,
            HxGenericElement<HxType>,
            HxAccessible<HxType>,
            HxNamed,
            HxProvider {

  /**
   * Initializes given type parts
   *
   * @param parts to initialize
   * @return this
   */
  HxType initialize(Part... parts);

  /**
   * Initializes given part
   *
   * @param part to initialize
   * @return this
   */
  HxType initialize(Part part);

  /**
   * @return byte-code version
   */
  Version getVersion();

  /**
   * @param version
   * @return
   */
  HxType setVersion(Version version);

  /**
   * @return
   */
  default Optional<HxSourceInfo> getSourceInfo() {
    return Optional.empty();
  }

  /**
   * @param source
   * @return
   */
  default HxType setSourceInfo(HxSourceInfo source) {
    return this;
  }

  /**
   * @return the standard Java classname, like:
   * <code>int</code>,
   * <code>byte[]</code>,
   * <code>java.lang.String</code>,
   * <code>java.lang.String[][]</code> or
   * <code>java.util.Map$Entry</code> etc.)
   */
  String getName();

  /**
   * @return simple name of this type
   * @see Class#getSimpleName()
   */
  String getSimpleName();

  /**
   * @return
   */
  default HxSort getSort() {
    return HxSort.fromName(getName());
  }

  /**
   * @return package of this type
   */
  default String getPackageName() {
    if(isArray() || isPrimitive()) {
      return null;
    }
    int index = getName().lastIndexOf(HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR);
    return index < 0 ? "" : getName().substring(0, index);
  }

  /**
   * @return the parent-type of this type
   */
  Optional<HxType> getSuperType();

  /**
   * Shortcut for: <code>this.setSuperType(getHaxxor().reference(superType))</code>
   *
   * @param superType to reference as super type
   * @return
   */
  default HxType setSuperType(String superType) {
    return setSuperType(getHaxxor().reference(superType));
}

  /**
   * @param superType
   * @return
   */
  HxType setSuperType(HxType superType);

  /**
   * @param superType
   * @return
   */
  default boolean hasSuperType(String superType) {
    return getSuperType().isPresent()?
            getSuperType().get().hasName(superType) :
           superType == null;
  }

  /**
   * @param superType
   * @return
   */
  default boolean hasSuperType(HxType superType) {
    return Objects.equals(superType, getSuperType());
  }

  /**
   * @return <b>true</b> if this type has a reference to a super type, <b>false</b> otherwise.
   */
  default boolean hasSuperType() {
    return getSuperType().isPresent();
  }

  /**
   * @return
   */
  List<HxType> getInterfaces();

  /**
   * @param interfaces
   * @return
   */
  HxType setInterfaces(List<HxType> interfaces);

  /**
   * @param interfaces
   * @return
   */
  default HxType setInterfaces(String... interfaces) {
    return setInterfaces(getHaxxor().referencesAsList(interfaces));
  }

  /**
   * @param interfaceName
   * @return
   */
  default boolean hasInterface(String interfaceName) {
    Objects.requireNonNull(interfaceName, "Interface name can't be null.");
    for(HxType itf : getInterfaces()) {
      if(interfaceName.equals(itf.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param hxInterface
   * @return
   */
  default boolean hasInterface(HxType hxInterface) {
    Objects.requireNonNull(hxInterface, "Interface can't be null.");
    return hasInterface(hxInterface.getName());
  }

  /**
   * @return count of slots that are needed to store a value of this type on stack or as local variable
   * @implSpec <code>long</code> and <code>double</code> take two slots and all other only one
   */
  default int getSlotSize() {
    final String name = getName();
    return "void".equals(name) ? 0 :
           ("long".equals(name) || "double".equals(name)) ? 2 : 1;
  }

  /**
   *
   * @param innerType
   * @return
   */
  default HxType addInnerType(HxType innerType) {
    initialize(Part.INNER_TYPES).getInnerTypes().add(innerType);
    return this;
  }

  /**
   * @return
   */
  List<HxType> getInnerTypes();

  /**
   * @param declaredTypes
   * @return
   */
  default HxType setInnerTypes(HxType ... declaredTypes) {
    return setInnerTypes(Arrays.asList(declaredTypes));
  }

  /**
   * @param declaredTypes
   * @return
   */
  HxType setInnerTypes(List<HxType> declaredTypes);

  /**
   * @return
   */
  Optional<HxType> getEnclosingType();

  /**
   * @return
   */
  Optional<HxMethod> getEnclosingMethod();

  /**
   * @param field to search for
   * @return <b>-1</b> if given field doesn't belong to this type,
   * otherwise zero-based position of the given field in the {@link #getFields()} list
   */
  default int indexOf(HxField field) {
    if(!equals(field.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for(HxField hxField : getFields()) {
      if(field == hxField || field.equals(hxField)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }

  /**
   * @param method to search for
   * @return <b>-1</b> if given method doesn't belong to this type,
   * otherwise zero-based position of the given method in the {@link #getMethods()} list
   */
  default int indexOf(HxMethod method) {
    if(!equals(method.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for(HxMethod hxMethod : getMethods()) {
      if(method == hxMethod || method.equals(hxMethod)) {
        return idx;
      }
      idx++;
    }
    return -1;
  }
  /**
   * @return
   */
  List<HxField> getFields();

  /**
   * @param fields
   * @return
   */
  HxType setFields(List<HxField> fields);

  /**
   * Adds given field to this type at specific position
   *
   * @param field to add
   * @return this
   * @throws IllegalArgumentException if given field is already present in this type
   */
  default HxType addField(HxField field) {
    return addFieldAt(getFields().size(), field);
  }

  /**
   * Adds given field to this type at specific position
   *
   * @param index where to insert given field
   * @param field to add
   * @return this
   * @throws IllegalArgumentException if given field is already present in this type
   */
  HxType addFieldAt(int index,
                    HxField field);

  /**
   * @param field
   * @return this
   */
  HxType removeField(HxField field);

  /**
   * Gets a field with given name
   *
   * @param name of a field to search
   * @return {@link Optional#empty() empty} or a field with given name
   */
  Optional<HxField> findField(String name);

  /**
   * Checks whether there is a field with given name or not
   *
   * @param name of a field
   * @return <b>true</b> if there is a field with given name, <b>false</b> otherwise.
   */
  default boolean hasField(String name) {
    return findField(name).isPresent();
  }

  /**
   * @return
   */
  List<HxMethod> getMethods();

  /**
   * @param name
   * @return
   */
  Collection<HxMethod> getMethods(String name);

  /**
   * @param methods
   * @return
   */
  HxType setMethods(List<HxMethod> methods);

  /**
   * @param method
   * @return
   */
  HxType removeMethod(HxMethod method);

  /**
   * @param method
   * @return
   */
  default HxType addMethod(HxMethod method) {
    return addMethodAt(getMethods().size(), method);
  }

  /**
   * @param index where to insert given method
   * @param method to add
   * @return
   */
  HxType addMethodAt(int index,
                     HxMethod method);

  /**
   * @param name of a method
   * @return {@link Optional#empty() empty} or a method with given name
   * @throws IllegalStateException if there is more than one method with given name
   */
  Optional<HxMethod> findMethod(String name);

  /**
   * @param name is name of the wanted method
   * @param descriptor is signature description of the wanted method
   * @return {@link Optional#empty() empty} or a method with given name and signature
   */
  Optional<HxMethod> findMethodDirectly(String name,
                                        String descriptor);

  /**
   * @param method
   * @return
   */
  default Optional<HxMethod> findMethod(Method method) {
    HxTypeReference returnTypeReference = getHaxxor().reference(method.getReturnType().getName());
    return findMethod(returnTypeReference,
                      method.getName(),
                      getHaxxor().referencesAsArray(toClassNames(getHaxxor(), method.getParameterTypes())));
  }

  /**
   * @param method
   * @return
   */
  default Optional<HxMethod> findMethod(HxMethod method) {
    return findMethod(method.getReturnType(),
                      method.getName(),
                      method.getParameterTypes());
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return {@link Optional#empty() empty} or a method with given name and signature
   */
  default Optional<HxMethod> findMethod(String returnType,
                                String name,
                                String... parameters) {
    return findMethod(getHaxxor().reference(returnType),
                      name,
                      getHaxxor().referencesAsArray(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(HxType returnType,
                                String name,
                                HxType... parameters) {
    return findMethod(returnType,
                      name,
                      Arrays.asList(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(HxType returnType,
                                String name,
                                List<HxType> parameters) {
    return findMethod(Optional.of(returnType), name, parameters);
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  Optional<HxMethod> findMethod(Optional<HxType> returnType,
                                String name,
                                List<HxType> parameters);

  /**
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                List<HxType> parameters) {
    return findMethod(Optional.empty(), name, parameters);
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                HxType ... parameters) {
    return findMethod(name, Arrays.asList(parameters));
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                String ... parameters) {
    return findMethod(name, getHaxxor().referencesAsArray(parameters));
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(Class<?> returnType,
                                        String name,
                                        Class<?> ... parameters) {
    return findMethod(getHaxxor().reference(returnType.getName()),
                      name,
                      getHaxxor().referencesAsArray(toClassNames(parameters)));
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        Class<?> ... parameters) {
    return findMethod(name, getHaxxor().referencesAsArray(toClassNames(parameters)));
  }

  /**
   * @param method
   * @return
   */
  default boolean hasMethod(Method method) {
    return findMethod(method).isPresent();
  }

  /**
   * @param name
   * @return
   */
  default boolean hasMethod(String name) {
    return findMethod(name).isPresent();
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(String returnType,
                            String name,
                            String... parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(HxType returnType,
                    String name,
                    HxType... parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * @param returnType
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(HxType returnType,
                    String name,
                    List<HxType> parameters) {
    return findMethod(returnType, name, parameters).isPresent();
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(String name,
                            Class<?>... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(Class<?> returnType,
                            String name,
                            Class<?>... parameters) {
    return findMethod(returnType,
                      name,
                      parameters).isPresent();
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(String name,
                            String... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(String name,
                            HxType... parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default boolean hasMethod(String name,
                            List<HxType> parameters) {
    return findMethod(name, parameters).isPresent();
  }

  /**
   * @return a collection with all defined constructors
   */
  default Collection<HxMethod> getConstructors() {
    return getMethods(HxConstants.CONSTRUCTOR_METHOD_NAME);
  }

  /**
   * @param constructor
   * @return
   */
  default HxType addConstructor(HxMethod constructor) {
    return addConstructorAt(getMethods().size(), constructor);
  }

  /**
   * @param index
   * @param constructor
   * @return
   */
  default HxType addConstructorAt(int index, HxMethod constructor) {
    if(!HxConstants.CONSTRUCTOR_METHOD_NAME.equals(constructor)) {
      throw new IllegalArgumentException("Not a constructor: "+constructor);
    }
    return addMethodAt(getMethods().size(), constructor);
  }

  /**
   * @param constructor
   * @return
   */
  default HxType removeConstructor(HxMethod constructor) {
    if(!HxConstants.CONSTRUCTOR_METHOD_NAME.equals(constructor)) {
      throw new IllegalArgumentException("Not a constructor: "+constructor);
    }
    return removeMethod(constructor);
  }

  /**
   * @param descriptor is signature description of the constructor
   * @return
   */
  Optional<HxMethod> findConstructorDirectly(String descriptor);

  /**
   * @param constructor
   * @return
   */
  default Optional<HxMethod> findConstructor(Constructor<?> constructor) {
    return findConstructor(toClassNames(getHaxxor(), constructor.getParameterTypes()));
  }

  /**
   * @param constructor
   * @return
   */
  default Optional<HxMethod> findConstructor(HxMethod constructor) {
    return findConstructor(constructor.getParameterTypes());
  }

  /**
   * @param signature of wanted constructor as list
   * @return
   */
  Optional<HxMethod> findConstructor(List<HxType> signature);

  /**
   * @param signature of wanted constructor as a HxType array
   * @return
   */
  default Optional<HxMethod> findConstructor(HxType... signature) {
    return findConstructor(Arrays.asList(signature));
  }

  /**
   * @param signature of wanted constructor as an array of type names
   * @return
   */
  default Optional<HxMethod> findConstructor(String... signature) {
    return findConstructor(getHaxxor().referencesAsArray(signature));
  }

  /**
   * @param signature of wanted constructor as an array of type names
   * @return
   */
  default Optional<HxMethod> findConstructor(Class<?>... signature) {
    return findConstructor(getHaxxor().referencesAsArray(toClassNames(signature)));
  }

  /**
   * @param constructor
   * @return
   */
  default boolean hasConstructor(Constructor<?> constructor) {
    return hasConstructor(toClassNames(getHaxxor(), constructor.getParameterTypes()));
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(Class<?> ... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(String... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(HxType... parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @param parameters
   * @return
   */
  default boolean hasConstructor(List<HxType> parameters) {
    return findConstructor(parameters).isPresent();
  }

  /**
   * @return
   */
  Optional<HxGenericType> getGenericType();

  /**
   * Shortcut for: <code>getName().equals(haxxor.toNormalizedClassname(className))</code>
   *
   * @param className to check against
   * @return <b>true</b> if name of this type is equal to the given one, <b>false</b> otherwise.
   */
  default boolean hasName(String className) {
    return getName().equals(getHaxxor().toNormalizedClassname(className));
  }

  /**
   * Shortcut for: <code>otherType.isAssignableFrom(this)</code>
   *
   * @param otherType to check against
   * @return
   */
  default boolean isTypeOf(HxType otherType) {
    return otherType.isAssignableFrom(this);
  }

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  boolean isAssignableFrom(HxType otherType);

  /**
   * @return
   */
  default Collection<HxField> fields() {
    return fields((f) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxField> fields(Predicate<HxField> predicate) {
    return fields(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxField> fields(Predicate<HxField> predicate,
                             boolean recursive);

  /**
   * @return
   */
  default Collection<HxMethod> methods() {
    return methods((m) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxMethod> methods(Predicate<HxMethod> predicate) {
    return methods(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxMethod> methods(Predicate<HxMethod> predicate,
                               boolean recursive);

  /**
   * @return
   */
  default Collection<HxMethod> constructors() {
    return constructors((c) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxMethod> constructors(Predicate<HxMethod> predicate) {
    return constructors(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxMethod> constructors(Predicate<HxMethod> predicate,
                                         boolean recursive);

  /**
   * @return
   */
  default Collection<HxType> types() {
    return types((t) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxType> types(Predicate<HxType> predicate) {
    return types(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxType> types(Predicate<HxType> predicate,
                           boolean recursive);

  /**
   * @return
   */
  default Collection<HxType> interfaces() {
    return interfaces((i) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxType> interfaces(Predicate<HxType> predicate) {
    return interfaces(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxType> interfaces(Predicate<HxType> predicate,
                                boolean recursive);

  /**
   * @return component type of this array type or <b>null</b> if it isn't array type
   */
  Optional<HxType> getComponentType();

  /**
   * @return whether this type represents array type or not
   */
  default boolean isArray() {
    return getDimension() > 0;
  }

  /**
   * @return a number of dimension that this type has
   */
  int getDimension();

  /**
   * @return <b>true</b> if this is a primitive type, <b>false</b> otherwise.
   */
  boolean isPrimitive();

  /**
   * @return <b>true</b> if this is a local type, <b>false</b> otherwise.
   */
  boolean isLocalType();

  /**
   * @return <b>true</b> if this is a member type, <b>false</b> otherwise.
   */
  boolean isMemberType();

  /**
   * @return <b>true</b> if this is a final type, <b>false</b> otherwise.
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  /**
   * @return <b>true</b> if this is a public type, <b>false</b> otherwise.
   */
  default boolean isPublic() {
    return hasModifiers(Modifiers.PUBLIC);
  }

  /**
   * @return <b>true</b> if this is a protected type, <b>false</b> otherwise.
   */
  default boolean isProtected() {
    return hasModifiers(Modifiers.PROTECTED);
  }

  /**
   * @return <b>true</b> if this is a private type, <b>false</b> otherwise.
   */
  default boolean isPrivate() {
    return hasModifiers(Modifiers.PRIVATE);
  }

  /**
   * @return <b>true</b> if this is an internal (package-private) type, <b>false</b> otherwise.
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  /**
   * @return <b>true</b> if this is an abstract type, <b>false</b> otherwise.
   */
  default boolean isAbstract() {
    return hasModifiers(Modifiers.ABSTRACT);
  }

  /**
   * @return <b>true</b> if this is an interface type, <b>false</b> otherwise.
   */
  default boolean isInterface() {
    return hasModifiers(Modifiers.INTERFACE);
  }

  /**
   * @return <b>true</b> if this is an enum type, <b>false</b> otherwise.
   */
  default boolean isEnum() {
    return hasModifiers(Modifiers.ENUM);
  }

  /**
   * @return <b>true</b> if this is an annotation type, <b>false</b> otherwise.
   */
  default boolean isAnnotation() {
    return hasModifiers(Modifiers.ANNOTATION);
  }

  /**
   * @return <b>true</b> if this is an anonymous type, <b>false</b> otherwise.
   */
  default boolean isAnonymous() {
    return getSimpleName().isEmpty();
  }

  /**
   * @return
   */
  default boolean isReference() {
    return this instanceof HxTypeReference;
  }

  /**
   * @return
   */
  default boolean isVoid() {
    return "void".equals(getName());
  }

  /**
   * @return <b>true</b> if this type can be instantiated, <b>false</b> otherwise.
   */
  default boolean isInstantiable() {
    return !isPrimitive() &&
      !hasModifiers(Modifiers.ABSTRACT) &&
      !hasModifiers(Modifiers.INTERFACE);
  }

  /**
   * @return
   */
  default Class<?> loadClass()
  throws ClassNotFoundException {
    return loadClass(getHaxxor().getClassLoader());
  }

  /**
   * @param classLoader
   * @return
   */
  Class<?> loadClass(ClassLoader classLoader)
  throws ClassNotFoundException;

  /**
   * @return internal classname of this type
   */
  default String toInternalName() {
    if(isPrimitive() || isArray()) {
      return toDescriptor();
    }
    return getName().replace(
        HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR,
        HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR
    );
  }

  /**
   * @return parameter descriptor of this type
   */
  default String toDescriptor() {
    return toDescriptor(new StringBuilder(getName().length() + 2)).toString();
  }

  /**
   * @param builder to use
   * @return parameter descriptor of this type printed to the given builder instance
   */
  default Appendable toDescriptor(Appendable builder) {
    try {
      if (isArray()) {
        builder.append('[');
        return getComponentType().get().toDescriptor(builder);
      }
      builder.append('L');
      final String name = getName();
      for(int i = 0, len = name.length(); i<len; i++) {
        char c = name.charAt(i);
        if(c == HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR) {
          c = HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR;
        }
        builder.append(c);
      }
      builder.append(';');
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return builder;
  }

  /**
   * @return a reference to this type
   */
  HxTypeReference toReference();

  /**
   * Creates bytecode representation of the actual type's state.
   *
   * @return the actual state of this type as loadable bytecode
   */
  byte[] toByteCode();

  /**
   */
  enum Version {
    V1_5(Opcodes.V1_5),
    V1_6(Opcodes.V1_6),
    V1_7(Opcodes.V1_7),
    V1_8(Opcodes.V1_8);

    final int code;

    Version(int code) {
      this.code = code;
    }

    public static Version of(int ver) {
      switch (ver) {
        case Opcodes.V1_5: return V1_5;
        case Opcodes.V1_6: return V1_6;
        case Opcodes.V1_7: return V1_7;
        case Opcodes.V1_8: return V1_8;
      }
      throw new IllegalArgumentException("Unsupported version: " + ver);
    }

    public int getCode() {
      return code;
    }
  }

  /**
   *
   */
  enum Part {
    DEFAULTS,
    ANNOTATIONS,
    INTERFACES,
    FIELDS,
    METHODS,
    INNER_TYPES
  }

  /**
   *
   */
  enum Modifiers
      implements HxModifier {
    // class, field, method
    PUBLIC(Opcodes.ACC_PUBLIC),
    // class, field, method
    PRIVATE(Opcodes.ACC_PRIVATE),
    // class, field, method
    PROTECTED(Opcodes.ACC_PROTECTED),
    // class, field, method
    STATIC(Opcodes.ACC_STATIC),
    // class, field, method, parameter
    FINAL(Opcodes.ACC_FINAL),
    // class
    SUPER(Opcodes.ACC_SUPER),
    // class
    INTERFACE(Opcodes.ACC_INTERFACE),
    // class, method
    ABSTRACT(Opcodes.ACC_ABSTRACT),
    // class, field, method, parameter
    SYNTHETIC(Opcodes.ACC_SYNTHETIC),
    // class
    ANNOTATION(Opcodes.ACC_ANNOTATION),
    // class(?) field inner
    ENUM(Opcodes.ACC_ENUM);

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given type's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    @Override
    public int toBit() {
      return bit;
    }
  }
}
