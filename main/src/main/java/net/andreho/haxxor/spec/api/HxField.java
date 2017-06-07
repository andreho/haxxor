package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

import java.util.EnumSet;
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
            HxIndexed,
            HxProvider,
            Cloneable {

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

  /**
   * @return <b>true</b> if this field has protected visibility, <b>false</b> otherwise.
   */
  default boolean isProtected() {
    return hasModifiers(Modifiers.PROTECTED);
  }

  /**
   * @return <b>true</b> if this field has private visibility, <b>false</b> otherwise.
   */
  default boolean isPrivate() {
    return hasModifiers(Modifiers.PRIVATE);
  }

  /**
   * @return <b>true</b> if this field has package-private visibility, <b>false</b> otherwise.
   */
  default boolean isInternal() {
    return !isPublic() && !isProtected() && !isPrivate();
  }

  /**
   * @return <b>true</b> if this field defines volatile access, <b>false</b> otherwise.
   */
  default boolean isVolatile() {
    return hasModifiers(Modifiers.VOLATILE);
  }

  /**
   * @return <b>true</b> if this field is transient, <b>false</b> otherwise.
   */
  default boolean isTransient() {
    return hasModifiers(Modifiers.TRANSIENT);
  }

  /**
   * @return <b>true</b> if this field is synthetic, <b>false</b> otherwise.
   */
  default boolean isSynthetic() {
    return hasModifiers(Modifiers.SYNTHETIC);
  }

  /**
   * @return <b>true</b> if this field is static, <b>false</b> otherwise.
   */
  default boolean isStatic() {
    return hasModifiers(Modifiers.STATIC);
  }

  /**
   * @return <b>true</b> if this field is final, <b>false</b> otherwise.
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
  }

  /**
   * @return <b>true</b> if this field defines one of enum constants, <b>false</b> otherwise.
   */
  default boolean isEnum() {
    return hasModifiers(Modifiers.ENUM);
  }

  /**
   * @return <b>true</b> if this field instance has some generic information
   */
  default boolean isGeneric() {
    return getGenericSignature() != null && !getGenericSignature().isEmpty();
  }

  /**
   * @return raw generic signature of this element
   */
  default String getGenericSignature() {
    return "";
  }

  /**
   * @param genericSignature new raw generic signature
   * @return
   */
  default HxField setGenericSignature(String genericSignature) {
    return this;
  }

  /**
   * @return generic type of this field
   */
  default HxGeneric getGenericType() {
    return getType();
  }

  enum Modifiers
      implements HxModifier {
    PUBLIC(0x0001),
    // class, field, method
    PRIVATE(0x0002),
    // class, field, method
    PROTECTED(0x0004),
    // class, field, method
    STATIC(0x0008),
    // field, method
    FINAL(0x0010),
    // class, field, method, parameter
    VOLATILE(0x0040),
    // field
    TRANSIENT(0x0080),
    // field
    SYNTHETIC(0x1000),
    // class, field, method, parameter
    ENUM(0x4000); // class(?) field inner

    final int bit;

    Modifiers(int bit) {
      this.bit = bit;
    }

    /**
     * Transforms given bit-set to an {@link EnumSet} with appropriate type
     *
     * @param modifiers to transform
     * @return a set with extracted modifiers
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

      if ((modifiers & Opcodes.ACC_STATIC) != 0) {
        modifierSet.add(STATIC);
      }

      if ((modifiers & Opcodes.ACC_FINAL) != 0) {
        modifierSet.add(FINAL);
      } else if ((modifiers & Opcodes.ACC_VOLATILE) != 0) {
        modifierSet.add(VOLATILE);
      }

      if ((modifiers & Opcodes.ACC_TRANSIENT) != 0) {
        modifierSet.add(TRANSIENT);
      }

      if ((modifiers & Opcodes.ACC_SYNTHETIC) != 0) {
        modifierSet.add(SYNTHETIC);
      }

      if ((modifiers & Opcodes.ACC_ENUM) != 0) {
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
