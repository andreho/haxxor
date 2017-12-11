package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.util.Set;

/**
 * <u>Important notes on implementation:</u><br/>
 * Any field must be recognizable all the time uniquely.<br/>
 * That's why, it must provide following information at any time of its lifecycle: <br/>
 * <ul>
 * <li>a unique name within the declaring class through call to {@link #getName()}</li>
 * </ul>
 * <p>
 * Modifiers and other information aren't that important and don't identifies a field within a class uniquely.<br/>
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxField
  extends HxAnnotated<HxField>,
          HxMember<HxField>,
          HxOwned<HxField>,
          HxGeneric<HxField>,
          HxAccessible<HxField>,
          HxNamed,
          HxTyped,
          HxOrdered,
          HxProvider,
          Cloneable {

  int ALLOWED_MODIFIERS =
    Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL |
    Opcodes.ACC_VOLATILE | Opcodes.ACC_TRANSIENT | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_ENUM;

  enum Modifiers
    implements HxModifier {
    PUBLIC(Opcodes.ACC_PUBLIC),
    // class, field, method
    PRIVATE(Opcodes.ACC_PRIVATE),
    // class, field, method
    PROTECTED(Opcodes.ACC_PROTECTED),
    // class, field, method
    STATIC(Opcodes.ACC_STATIC),
    // field, method
    FINAL(Opcodes.ACC_FINAL),
    // class, field, method, parameter
    VOLATILE(Opcodes.ACC_VOLATILE),
    // field
    TRANSIENT(Opcodes.ACC_TRANSIENT),
    // field
    SYNTHETIC(Opcodes.ACC_SYNTHETIC),
    // class, field, method, parameter
    ENUM(Opcodes.ACC_ENUM); // class(?) field inner

    final int bit;

    /**
     * Transforms given modifiers to an equal enum-set
     *
     * @param modifiers to transform
     * @return enum-set representation of given field's modifiers
     */
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    /**
     * @param bit to represent
     */
    Modifiers(int bit) {
      this.bit = bit;
    }

    @Override
    public int toBit() {
      return bit;
    }
  }

  /**
   * @return a new cloned version of this field
   */
  HxField clone();

  /**
   * @param name of the cloned field's version
   * @return a new cloned version of this field but with another one name
   */
  HxField clone(String name);

  /**
   * @return <b>-1</b> if this field wasn't found or not bound to any type,
   * otherwise the zero-based position of this field in the corresponding collection of declaring type
   */
  @Override
  int getIndex();

  /**
   * @return name of this field
   */
  String getName();

  /**
   * @param type of this field
   * @return
   */
  HxField setType(HxType type);

  /**
   * @return type of this field
   */
  HxType getType();

  /**
   * @param name to match against
   * @return <b>true</b> if this field has the given name
   */
  default boolean hasName(String name) {
    return getName().equals(name);
  }

  /**
   * @param classname to match against
   * @return
   */
  default boolean hasType(String classname) {
    if(getType() == null) {
      return classname == null;
    }
    return getType().hasName(classname);
  }

  /**
   * @param type to match against
   * @return
   */
  default boolean hasType(HxType type) {
    return hasType(type.getName());
  }

  /**
   * @param cls to match against
   * @return
   */
  default boolean hasType(Class<?> cls) {
    return hasType(cls.getName());
  }

  /**
   * @param descriptor to match against
   * @return <b>true</b> if this field's type has the given descriptor
   */
  default boolean hasDescriptor(String descriptor) {
    return getType().hasDescriptor(descriptor);
  }

  /**
   * Modifies the type of this field
   *
   * @param type of this field
   * @return this
   */
  default HxField setType(String type) {
    return setType(getType().getHaxxor()
                            .reference(type));
  }

  /**
   * @return default value associated with this field if exists
   * (typically one of boxed values, e.g. Integer, Long, Boolean etc.)
   */
  Object getDefaultValue();

  /**
   * Modifies default value of this field
   *
   * @param value as default value of this field
   * @return this
   */
  HxField setDefaultValue(Object value);

  /**
   * Decides whether this field is compatible with a value of given type or not
   *
   * @param type to check against type of this field
   * @return <b>true</b> if this field may be assigned to a value of the given type, <b>false</b> if not.
   */
  default boolean isCompatibleWith(HxType type) {
    HxType fieldType = getType();
    if (fieldType != null) {
      return fieldType.isAssignableFrom(type);
    }
    return false;
  }

  /**
   * @return <b>true</b> if this field has public visibility, <b>false</b> otherwise.
   */
  default boolean isPublic() {
    return hasModifiers(Modifiers.PUBLIC);
  }

  default HxField makePublic() {
    return makeInternal().addModifier(Modifiers.PUBLIC);
  }

  /**
   * @return <b>true</b> if this field has protected visibility, <b>false</b> otherwise.
   */
  default boolean isProtected() {
    return hasModifiers(Modifiers.PROTECTED);
  }

  default HxField makeProtected() {
    return makeInternal().addModifier(Modifiers.PROTECTED);
  }

  /**
   * @return <b>true</b> if this field has private visibility, <b>false</b> otherwise.
   */
  default boolean isPrivate() {
    return hasModifiers(Modifiers.PRIVATE);
  }

  default HxField makePrivate() {
    return makeInternal().addModifier(Modifiers.PRIVATE);
  }

  /**
   * @return <b>true</b> if this field has package-private visibility, <b>false</b> otherwise.
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  default HxField makeInternal() {
    return removeModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED | Opcodes.ACC_PRIVATE);
  }

  /**
   * @return <b>true</b> if this field defines volatile access, <b>false</b> otherwise.
   */
  default boolean isVolatile() {
    return hasModifiers(Modifiers.VOLATILE);
  }

  default HxField makeVolatile() {
    return removeModifiers(Opcodes.ACC_FINAL).addModifier(Modifiers.VOLATILE);
  }

  /**
   * @return <b>true</b> if this field is transient, <b>false</b> otherwise.
   */
  default boolean isTransient() {
    return hasModifiers(Modifiers.TRANSIENT);
  }

  default HxField makeTransient() {
    return addModifier(Modifiers.TRANSIENT);
  }

  /**
   * @return <b>true</b> if this field is synthetic, <b>false</b> otherwise.
   */
  default boolean isSynthetic() {
    return hasModifiers(Modifiers.SYNTHETIC);
  }

  default HxField makeSynthetic() {
    return addModifier(Modifiers.SYNTHETIC);
  }

  /**
   * @return <b>true</b> if this field is static, <b>false</b> otherwise.
   */
  default boolean isStatic() {
    return hasModifiers(Modifiers.STATIC);
  }

  default HxField makeStatic() {
    return addModifier(Modifiers.STATIC);
  }

  /**
   * @return <b>true</b> if this field is final, <b>false</b> otherwise.
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  default HxField makeFinal() {
    return removeModifiers(Opcodes.ACC_VOLATILE).addModifier(Modifiers.FINAL);
  }

  /**
   * @return <b>true</b> if this field defines one of enum constants, <b>false</b> otherwise.
   */
  default boolean isEnum() {
    return hasModifiers(Modifiers.ENUM);
  }

  @Override
  HxType getDeclaringMember();

  /**
   * @return
   */
  default String toDescriptor() {
    if (getType() != null) {
      return getType().toDescriptor();
    }
    return null;
  }

  /**
   * @return generic type of this field
   */
  default HxGenericElement getGenericElement() {
    return getType();
  }
}
