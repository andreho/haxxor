package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

  int ALLOWED_MODIFIERS =
    Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL |
    Opcodes.ACC_SUPER | Opcodes.ACC_INTERFACE | Opcodes.ACC_ABSTRACT | Opcodes.ACC_ANNOTATION |
    Opcodes.ACC_ENUM | Opcodes.ACC_SYNTHETIC;

  /**
   */
  enum Version {
    V1_5(Opcodes.V1_5),
    V1_6(Opcodes.V1_6),
    V1_7(Opcodes.V1_7),
    V1_8(Opcodes.V1_8);

    final int code;

    public static Version of(int ver) {
      switch (ver) {
        case Opcodes.V1_5:
          return V1_5;
        case Opcodes.V1_6:
          return V1_6;
        case Opcodes.V1_7:
          return V1_7;
        case Opcodes.V1_8:
          return V1_8;
      }
      throw new IllegalArgumentException("Unsupported version: " + ver);
    }

    Version(int code) {
      this.code = code;
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

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given type's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }

  /**
   * Initializes given parts of this type
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
   * @return the standard binary Java classname of this type like: <br/>
   * <code>int</code>,
   * <code>byte[]</code>,
   * <code>java.lang.Object</code>,
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
   * @return sort of this type
   */
  default HxSort getSort() {
    return HxSort.fromName(getName());
  }

  /**
   * @return package of this type
   */
  default String getPackageName() {
    if (isArray() || isPrimitive()) {
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
   * @return this
   */
  default HxType setSuperType(String superType) {
    return setSuperType(getHaxxor().reference(superType));
  }

  /**
   * Shortcut for: <code>this.setSuperType(getHaxxor().reference(superClass.getName()))</code>
   *
   * @param superClass to reference as super type
   * @return this
   */
  default HxType setSuperType(Class<?> superClass) {
    if (superClass.isPrimitive() || superClass.isArray() || superClass.isInterface()) {
      throw new IllegalArgumentException("Class can't be used as superclass: " + superClass);
    }
    return setSuperType(getHaxxor().reference(superClass.getName()));
  }

  /**
   * @param superType of this type
   * @return this
   */
  HxType setSuperType(HxType superType);

  /**
   * @param superType
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(String superType) {
    return getSuperType().isPresent() ?
           getSuperType().get().hasName(superType) :
           superType == null;
  }

  /**
   * @param superType
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(HxType superType) {
    return Objects.equals(getSuperType(), superType);
  }

  /**
   * @param superClass
   * @return <b>true</b> if this type has the given supertype
   */
  default boolean hasSuperType(Class<?> superClass) {
    return
      !superClass.isPrimitive() &&
      !superClass.isInterface() &&
      !superClass.isArray() &&
      !superClass.isEnum() &&
      hasSuperType(superClass.getName());
  }

  /**
   * @return <b>true</b> if this type has a reference to a supertype, <b>false</b> otherwise.
   */
  default boolean hasSuperType() {
    return getSuperType().isPresent();
  }

  /**
   * @return
   */
  List<HxType> getInterfaces();

  /**
   * @param interfaces of this type
   * @return this
   */
  default HxType setInterfaces(String... interfaces) {
    return setInterfaces(getHaxxor().referencesAsList(interfaces));
  }

  /**
   * @param interfaces of this type
   * @return this
   */
  HxType setInterfaces(List<HxType> interfaces);

  default HxType addInterface(String type) {
    final HxTypeReference typeReference = getHaxxor().reference(type);
    return addInterface(typeReference);
  }

  default HxType addInterface(Class<?> type) {
    if (!type.isInterface()) {
      throw new IllegalArgumentException("Not an interface: " + type);
    }
    return addInterface(type.getName());
  }

  default HxType addInterface(HxType type) {
    if (!type.isInterface()) {
      throw new IllegalArgumentException("Not an interface: " + type);
    }
    final List<HxType> interfaces = initialize(Part.INTERFACES).getInterfaces();
    if (!interfaces.contains(type)) {
      interfaces.add(type);
    }
    return this;
  }

  /**
   * @param interfaceName to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final String interfaceName) {
    Objects.requireNonNull(interfaceName, "Interface name can't be null.");
    for (HxType itf : getInterfaces()) {
      if (itf.hasName(interfaceName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param hxInterface to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final HxType hxInterface) {
    Objects.requireNonNull(hxInterface, "Interface can't be null.");
    return hasInterface(hxInterface.getName());
  }

  /**
   * @param itf to look for
   * @return <b>true</b> if this type <code>implements</code> the given one interface
   */
  default boolean hasInterface(final Class<?> itf) {
    Objects.requireNonNull(itf, "Interface can't be null.");
    return itf.isInterface() && hasInterface(itf.getName());
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
   * @param innerType of this type
   * @return this
   */
  default HxType addInnerType(final HxType innerType) {
    initialize(Part.INNER_TYPES).getInnerTypes().add(innerType);
    return this;
  }

  /**
   * @return a list with all registered inner types of this type
   */
  List<HxType> getInnerTypes();

  /**
   * @param declaredTypes a list with all declared inner types
   * @return this
   */
  HxType setInnerTypes(List<HxType> declaredTypes);

  /**
   * @param declaredTypes a list with all declared inner types
   * @return this
   */
  default HxType setInnerTypes(HxType... declaredTypes) {
    return setInnerTypes(Arrays.asList(declaredTypes));
  }

  /**
   * If this type is an inner class then this method return a type reference to the enclosing type
   *
   * @return the enclosing type of this type if present
   */
  Optional<HxType> getEnclosingType();

  /**
   * If this type is an inner local class then this method return a type reference to the enclosing method/constructor
   *
   * @return the enclosing method of this type if present
   */
  Optional<HxMethod> getEnclosingMethod();

  /**
   * @param field to search for
   * @return <b>-1</b> if given field doesn't belong to this type,
   * otherwise zero-based position of the given field in the {@link #getFields()} list
   */
  default int indexOf(HxField field) {
    if (!equals(field.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxField hxField : getFields()) {
      if (field == hxField || field.equals(hxField)) {
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
    if (!equals(method.getDeclaringMember())) {
      return -1;
    }
    int idx = 0;
    for (HxMethod hxMethod : getMethods()) {
      if (method == hxMethod || method.equals(hxMethod)) {
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
   * @param field to remove
   * @return this
   * @throws IllegalArgumentException if given field doesn't belong to this type
   */
  HxType removeField(HxField field);

  /**
   * Gets the first available field with given name
   *
   * @param name of a field to search
   * @return {@link Optional#empty() empty} or a field with given name
   */
  Optional<HxField> findField(String name);

  /**
   * @param name       of the searched field
   * @param descriptor form of the searched field's type
   * @return {@link Optional#empty() empty} or a field with given name and descriptor
   */
  Optional<HxField> findFieldDirectly(String name,
                                      String descriptor);

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
   * @param methods
   * @return
   */
  HxType setMethods(List<HxMethod> methods);

  /**
   * @param name
   * @return
   */
  Collection<HxMethod> getMethods(String name);

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
   * @param index  where to insert given method
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
   * @param name       is name of the wanted method
   * @param descriptor is signature descriptor of the desired method
   * @return {@link Optional#empty() empty} or a method with given name and signature
   */
  Optional<HxMethod> findMethodDirectly(String name,
                                        String descriptor);

  /**
   * @param method
   * @return
   */
  default Optional<HxMethod> findMethod(Method method) {
    return findMethod(method.getReturnType(), method.getName(), method.getParameterTypes());
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
                                        HxType... parameters) {
    return findMethod(name, Arrays.asList(parameters));
  }

  /**
   * @param name
   * @param parameters
   * @return
   */
  default Optional<HxMethod> findMethod(String name,
                                        String... parameters) {
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
                                        Class<?>... parameters) {
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
                                        Class<?>... parameters) {
    return findMethod(name, getHaxxor().referencesAsArray(toClassNames(parameters)));
  }

  /**
   * @return
   */
  default Optional<HxMethod> findClassInitializer() {
    return findMethodDirectly(HxConstants.CLASS_INITIALIZER_METHOD_NAME, "()V");
  }

  /**
   * @param method
   * @return
   */
  default boolean hasMethod(Method method) {
    return findMethod(method).isPresent();
  }

  /**
   * @param method
   * @return
   */
  default boolean hasMethod(HxMethod method) {
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
  default HxType addConstructorAt(int index,
                                  HxMethod constructor) {
    if (!HxConstants.CONSTRUCTOR_METHOD_NAME.equals(constructor.getName())) {
      throw new IllegalArgumentException("Not a constructor: " + constructor);
    }
    return addMethodAt(index, constructor);
  }

  /**
   * @param constructor
   * @return
   */
  default HxType removeConstructor(HxMethod constructor) {
    if (!HxConstants.CONSTRUCTOR_METHOD_NAME.equals(constructor.getName())) {
      throw new IllegalArgumentException("Not a constructor: " + constructor);
    }
    return removeMethod(constructor);
  }

  /**
   * @param descriptor is signature description of the constructor to find
   * @return
   */
  Optional<HxMethod> findConstructorDirectly(String descriptor);

  /**
   * @return
   */
  default Optional<HxMethod> findDefaultConstructor() {
    return findConstructor(Collections.emptyList());
  }

  /**
   * @return
   */
  default Optional<HxMethod> findOrCreateDefaultConstructor() {
    return findDefaultConstructor();
  }

  /**
   * @return
   */
  default Optional<HxMethod> findOrCreateClassInitializer() {
    return findClassInitializer();
  }

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
   * @return a set with constructors that propagate their invocation to a constructor defined by the super type
   */
  default Collection<HxMethod> findForwardingConstructors() {
    return Collections.emptySet();
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
  default boolean hasConstructor(Class<?>... parameters) {
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
  default boolean hasDefaultConstructor() {
    return findDefaultConstructor().isPresent();
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
   * @param className to check against current classname and all names of parent types
   * @return <b>true</b> if name of this type is equal to the given one or if anyone of super types has the given name,
   * <b>false</b> otherwise.
   */
  default boolean hasNameViaExtends(String className) {
    if (!hasName(className)) {
      final Optional<HxType> superType = getSuperType();
      return superType.isPresent() &&
             superType.get().hasNameViaExtends(className);
    }
    return true;
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
   */
  default boolean isAssignableFrom(String otherType) {
    return isAssignableFrom(getHaxxor().reference(otherType));
  }

  /**
   * @param otherType
   * @return
   */
  default boolean isAssignableFrom(Class<?> otherType) {
    return isAssignableFrom(getHaxxor().reference(otherType.getName()));
  }

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  boolean isAssignableFrom(HxType otherType);

  /**
   * This method calculates a number of hops from this type to other subtype or interface-type
   *
   * @param otherType as destination
   * @return <b>-1</b> if there isn't any way to reach given type, otherwise a calculated distance
   * @implNote by default this method uses array-cost equal to 3, extends-cost equal to 15 and interface-cost equal
   * to 600
   */
  default int distanceTo(HxType otherType) {
    return distanceTo(otherType, 3, 15, 600);
  }

  /**
   * This method calculates a number of hops from this type to other super- or interface-type
   *
   * @param otherType     as destination
   * @param arrayCost     for arrays destinations
   * @param extendsCost   for super-type visiting
   * @param interfaceCost for interface-type visiting
   * @return <b>-1</b> if there isn't any way to reach given type, otherwise a calculated distance
   */
  default int distanceTo(HxType otherType,
                         int arrayCost,
                         int extendsCost,
                         int interfaceCost) {
    return hasName(otherType.getName()) ? 0 : -1;
  }

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
   * @return whether this type represents an array type or not
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

  default HxType makeFinal() {
    return removeModifiers(Modifiers.ABSTRACT, Modifiers.INTERFACE, Modifiers.ANNOTATION)
      .addModifiers(Modifiers.FINAL, Modifiers.SUPER);
  }

  /**
   * @return <b>true</b> if this is a public type, <b>false</b> otherwise.
   */
  default boolean isPublic() {
    return hasModifiers(Modifiers.PUBLIC);
  }

  default HxType makePublic() {
    return makeInternal().addModifier(Modifiers.PUBLIC);
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

  default HxType makeInternal() {
    return removeModifiers(Modifiers.PUBLIC, Modifiers.PROTECTED, Modifiers.PRIVATE);
  }

  /**
   * @return <b>true</b> if this is an abstract type, <b>false</b> otherwise.
   */
  default boolean isAbstract() {
    return hasModifiers(Modifiers.ABSTRACT);
  }

  default HxType makeAbstract() {
    return removeModifier(Modifiers.FINAL).addModifier(Modifiers.ABSTRACT);
  }

  /**
   * @return <b>true</b> if this is an interface type, <b>false</b> otherwise.
   */
  default boolean isInterface() {
    return hasModifiers(Modifiers.INTERFACE);
  }

  default HxType makeInterface() {
    return makeAbstract() //removes final and sets abstract flag
      .removeModifiers(Modifiers.SUPER, Modifiers.ENUM)
      .addModifier(Modifiers.INTERFACE);
  }

  /**
   * @return <b>true</b> if this is an enum type, <b>false</b> otherwise.
   */
  default boolean isEnum() {
    return hasModifiers(Modifiers.ENUM);
  }

  default HxType makeEnum() {
    return makeFinal().addModifier(Modifiers.ENUM);
  }

  /**
   * @return <b>true</b> if this is an annotation type, <b>false</b> otherwise.
   */
  default boolean isAnnotation() {
    return hasModifiers(Modifiers.ANNOTATION);
  }

  default HxType makeAnnotation() {
    return makeInterface()
      .addModifier(Modifiers.ANNOTATION);
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
   * @param classLoader where the referenced type prototype must be loaded
   * @return
   */
  Class<?> loadClass(ClassLoader classLoader)
  throws ClassNotFoundException;

  /**
   * @return internal classname of this type
   */
  default String toInternalName() {
    if (isPrimitive() || isArray()) {
      return toDescriptor();
    }
    return getName().replace(
      HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR,
      HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR
    );
  }

  /**
   * @param descriptor
   * @return <b>true</b> if this type has the given descriptor
   */
  default boolean hasDescriptor(String descriptor) {
    return descriptor.equals(toDescriptor());
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
      for (int i = 0, len = name.length(); i < len; i++) {
        char c = name.charAt(i);
        if (c == HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR) {
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
   * @param stubClass to wire in
   * @return a list with all added elements that belong to the given stub class
   * @see net.andreho.haxxor.spec.api.stub.Stub
   */
  default List<HxMember<?>> addTemplate(Class<?> stubClass) {
    return addTemplate(getHaxxor().resolve(stubClass));
  }

  /**
   * @param stubType to wire in
   * @return a list with all added elements that belong to the given stub class
   * @see net.andreho.haxxor.spec.api.stub.Stub
   */
  default List<HxMember<?>> addTemplate(HxType stubType) {
    return Collections.emptyList();
  }
}
