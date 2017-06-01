package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public interface HxType
    extends HxAnnotated<HxType>,
            HxMember<HxType>,
            HxOwned<HxType>,
            HxGeneric<HxType>,
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

  //----------------------------------------------------------------------------------------------------------------

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
   * @return the internal name of this type (e.g.: I, [Z, java/lang/String, [java/lang/Object or java/util/Map$Entry
   * etc.)
   */
  String getInternalName();

  /**
   * @return a Java like type name
   */
  String getJavaName();

  /**
   * @return the parent-type of this type
   */
  HxType getSuperType();

  /**
   * Shortcut for: <code>this.setSuperType(getHaxxor().reference(superType))</code>
   *
   * @param superType to reference as super type
   * @return
   */
  HxType setSuperType(String superType);

  /**
   * @param superType
   * @return
   */
  HxType setSuperType(HxType superType);

  /**
   * @return
   */
  Collection<HxType> getInterfaces();

  /**
   * @param interfaces
   * @return
   */
  HxType setInterfaces(Collection<HxType> interfaces);

  /**
   * @return count of slots that are needed to store a value of this type (e.g.: on stack or as local variable)
   */
  int getSlotsCount();

  /**
   * @return
   */
  boolean isReference();

  /**
   * @return
   */
  Collection<HxType> getDeclaredTypes();

  /**
   * @param declaredTypes
   * @return
   */
  HxType setDeclaredTypes(Collection<HxType> declaredTypes);

  /**
   * Adds given field to this type
   *
   * @param field
   * @return
   * @throws IllegalArgumentException if given field is already present in this type
   */
  HxType addField(HxField field);

  /**
   * Replaces corresponding field by name with given field version
   *
   * @param field to use
   * @return this
   * @throws IllegalArgumentException if given field isn't present in this type
   */
  HxType updateField(HxField field);

  /**
   * @param field
   * @return this
   */
  HxType removeField(HxField field);

  /**
   * Gets a field with given name
   *
   * @param name of a field to search
   * @return <b>null</b> or a field with given name
   */
  HxField getField(String name);

  /**
   * Checks whether there is a field with given name or not
   *
   * @param name of a field
   * @return <b>true</b> if there is a field with given name, <b>false</b> otherwise.
   */
  boolean hasField(String name);

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
  HxType addMethod(HxMethod method);

  /**
   * @param name
   * @return
   * @throws IllegalStateException
   */
  HxMethod getMethod(String name);

  /**
   * @param name is name of wanted method
   * @param desc is signature description of wanted method
   * @return
   */
  HxMethod getMethodDirectly(String name, String desc);

  /**
   * @param name
   * @param returnType
   * @param parameters
   * @return
   */
  HxMethod getMethod(String name, String returnType, String... parameters);

  /**
   * @param name
   * @param returnType
   * @param parameters
   * @return
   */
  HxMethod getMethod(String name, HxType returnType, HxType... parameters);

  /**
   * @param name
   * @param returnType
   * @param parameters
   * @return
   */
  HxMethod getMethod(String name, HxType returnType, List<HxType> parameters);

  /**
   * @param name
   * @param returnType
   * @param parameters
   * @return
   */
  boolean hasMethod(String name, String returnType, String... parameters);

  /**
   * @param name
   * @param returnType
   * @param parameters
   * @return
   */
  boolean hasMethod(String name, HxType returnType, HxType... parameters);

  /**
   * @param name
   * @param returnType
   * @param signature
   * @return
   */
  boolean hasMethod(String name, HxType returnType, List<HxType> signature);

  /**
   * @return
   */
  List<HxConstructor> getConstructors();

  /**
   * @param constructors
   * @return
   */
  HxType setConstructors(List<HxConstructor> constructors);

  /**
   * @param constructor
   * @return
   */
  HxType addConstructor(HxConstructor constructor);

  /**
   * @param signature of wanted constructor as list
   * @return
   */
  HxConstructor getConstructorWithParameters(List<HxParameter<HxConstructor>> signature);

  /**
   * @param signature of wanted constructor as list
   * @return
   */
  HxConstructor getConstructor(List<HxType> signature);

  /**
   * @param signature of wanted constructor as a HxType array
   * @return
   */
  HxConstructor getConstructor(HxType... signature);

  /**
   * @param signature of wanted constructor as an array of type names
   * @return
   */
  HxConstructor getConstructor(String... signature);

  /**
   * @param signature
   * @return
   */
  boolean hasConstructor(String... signature);

  /**
   * @param signature
   * @return
   */
  boolean hasConstructor(HxType... signature);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return whether this type has a generic specification or not
   */
  boolean isGeneric();

  /**
   * @return raw generic signature of this element
   */
  String getGenericSignature();

  /**
   * @param genericSignature new raw generic signature
   * @return current generic signature of this method as whole including parameters and return type
   */
  HxType setGenericSignature(String genericSignature);

  /**
   * @return
   */
  HxGeneric getGenericSuperType();

  /**
   * @return
   */
  List<HxGeneric> getGenericInterfaces();

  //----------------------------------------------------------------------------------------------------------------

  /**
   * Shortcut for: <code>getInternalName().equals(className)</code>
   *
   * @param className to check against
   * @return <b>true</b> if name of this type is equal to the given one, <b>false</b> otherwise.
   */
  boolean is(String className);

  /**
   * Shortcut for: <code>otherType.isAssignableFrom(this)</code>
   *
   * @param otherType to check against
   * @return
   */
  boolean isTypeOf(HxType otherType);

  /**
   * @param otherType
   * @return
   * @see Class#isAssignableFrom(Class)
   */
  boolean isAssignableFrom(HxType otherType);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  Collection<HxField> fields();

  /**
   * @param predicate
   * @return
   */
  Collection<HxField> fields(Predicate<HxField> predicate);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxField> fields(Predicate<HxField> predicate, boolean recursive);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  Collection<HxMethod> methods();

  /**
   * @param predicate
   * @return
   */
  Collection<HxMethod> methods(Predicate<HxMethod> predicate);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxMethod> methods(Predicate<HxMethod> predicate, boolean recursive);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  Collection<HxConstructor> constructors();

  /**
   * @param predicate
   * @return
   */
  Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxConstructor> constructors(Predicate<HxConstructor> predicate, boolean recursive);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  Collection<HxType> types();

  /**
   * @param predicate
   * @return
   */
  Collection<HxType> types(Predicate<HxType> predicate);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxType> types(Predicate<HxType> predicate, boolean recursive);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  Collection<HxType> interfaces();

  /**
   * @param predicate
   * @return
   */
  Collection<HxType> interfaces(Predicate<HxType> predicate);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxType> interfaces(Predicate<HxType> predicate, boolean recursive);

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   */
  HxType getEnclosingType();

  /**
   * @return
   */
  HxMethod getEnclosingMethod();

  /**
   * @return
   */
  HxConstructor getEnclosingConstructor();

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return
   * @see Class#getSimpleBinaryName()
   */
  String getSimpleBinaryName();

  /**
   * @return simple type name of this type
   * @see Class#getSimpleName()
   */
  String getSimpleName();

  /**
   * @return package of this type
   */
  String getPackage();

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return component type of this array type or <b>null</b> if it isn't array type
   */
  HxType getComponentType();

  /**
   * @return whether this type represents array type or not
   */
  boolean isArray();

  /**
   * @return a number of dimension that this type has
   */
  int getDimension();

  //----------------------------------------------------------------------------------------------------------------

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

  //----------------------------------------------------------------------------------------------------------------

  /**
   * @return <b>true</b> if this is a final type, <b>false</b> otherwise.
   */
  boolean isFinal();

  /**
   * @return <b>true</b> if this is a public type, <b>false</b> otherwise.
   */
  boolean isPublic();

  /**
   * @return <b>true</b> if this is a protected type, <b>false</b> otherwise.
   */
  boolean isProtected();

  /**
   * @return <b>true</b> if this is a private type, <b>false</b> otherwise.
   */
  boolean isPrivate();

  /**
   * @return <b>true</b> if this is an internal (package-private) type, <b>false</b> otherwise.
   */
  boolean isInternal();

  /**
   * @return <b>true</b> if this is an abstract type, <b>false</b> otherwise.
   */
  boolean isAbstract();

  /**
   * @return <b>true</b> if this is an interface type, <b>false</b> otherwise.
   */
  boolean isInterface();

  /**
   * @return <b>true</b> if this is an enum type, <b>false</b> otherwise.
   */
  boolean isEnum();

  /**
   * @return <b>true</b> if this is an annotation type, <b>false</b> otherwise.
   */
  boolean isAnnotation();

  /**
   * @return <b>true</b> if this is an anonymous type, <b>false</b> otherwise.
   */
  boolean isAnonymous();

  /**
   * @return
   */
  Class<?> loadClass();

  /**
   * @param classLoader
   * @return
   */
  Class<?> loadClass(ClassLoader classLoader);

  /**
   * @param builder to use
   * @return parameter descriptor of this type printed to the given builder instance
   */
  Appendable toDescriptor(Appendable builder);

  /**
   * @return parameter descriptor of this type
   */
  String toDescriptor();

  /**
   * @return a reference to this type
   */
  HxTypeReference toReference();

  /**
   * Creates bytecode representation of the actual type's state.
   * @return
   */
  byte[] toByteArray();

  /**
   */
  enum Version {
    V1_8(Opcodes.V1_8);

    final int code;

    Version(int code) {
      this.code = code;
    }

    public static Version of(int ver) {
      if (ver == Opcodes.V1_8) {
        return V1_8;
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
    CONSTRUCTORS,
    DECLARED_TYPES
  }

  /**
   *
   */
  enum Modifiers
      implements HxModifier {
    PUBLIC(0x0001),
    // class, field, method
    PRIVATE(0x0002),
    // class, field, method
    PROTECTED(0x0004),
    // class, field, method
    FINAL(0x0010),
    // class, field, method, parameter
    SUPER(0x0020),
    // class
    INTERFACE(0x0200),
    // class
    ABSTRACT(0x0400),
    // class, method
    SYNTHETIC(0x1000),
    // class, field, method, parameter
    ANNOTATION(0x2000),
    // class
    ENUM(0x4000); // class(?) field inner

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given modifiers
     */
    public static Set<Modifiers> toModifiers(int modifiers) {
      final Set<Modifiers> modifierSet = EnumSet.noneOf(Modifiers.class);

      if ((modifiers & Opcodes.ACC_PUBLIC) != 0) {
        modifierSet.add(PUBLIC);
      } else if ((modifiers & Opcodes.ACC_PROTECTED) != 0) {
        modifierSet.add(PROTECTED);
      } else if ((modifiers & Opcodes.ACC_PRIVATE) != 0) {
        modifierSet.add(PRIVATE);
      }

      if ((modifiers & Opcodes.ACC_SUPER) != 0) {
        modifierSet.add(SUPER);
      }

      if ((modifiers & Opcodes.ACC_ABSTRACT) != 0) {
        modifierSet.add(ABSTRACT);
      } else if ((modifiers & Opcodes.ACC_FINAL) != 0) {
        modifierSet.add(FINAL);
      }

      if ((modifiers & Opcodes.ACC_INTERFACE) != 0) {
        modifierSet.add(INTERFACE);
      }
      if ((modifiers & Opcodes.ACC_SYNTHETIC) != 0) {
        modifierSet.add(SYNTHETIC);
      }

      if ((modifiers & Opcodes.ACC_ANNOTATION) != 0) {
        modifierSet.add(ANNOTATION);
      } else if ((modifiers & Opcodes.ACC_ENUM) != 0) {
        modifierSet.add(ENUM);
      }

      return modifierSet;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }
}
