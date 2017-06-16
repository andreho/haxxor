package net.andreho.haxxor.spec.api;

import java.util.Objects;
import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMethod
    extends HxExecutable<HxMethod>,
            HxNamed,
            Cloneable {

  /**
   * @return
   */
  default HxMethod clone() {
    return clone(getName());
  }

  /**
   * @param name of the cloned method
   * @return a cloned version of this method with given name
   */
  HxMethod clone(String name);

  /**
   * @return name of this method
   */
  String getName();

  /**
   * @param name
   * @return
   */
  default boolean hasName(String name) {
    return name.equals(getName());
  }

  /**
   * @param returnType
   * @return
   */
  default boolean hasReturnType(String returnType) {
    if (getReturnType() == null) {
      return returnType == null;
    }
    return getReturnType().getName()
                          .equals(returnType);
  }

  /**
   * @param returnType
   * @return
   */
  default boolean hasReturnType(HxType returnType) {
    return hasReturnType(Objects.requireNonNull(returnType).getName());
  }

  /**
   * @return the return value of this method
   */
  HxType getReturnType();

  /**
   * @param returnType
   * @return
   * @implSpec return value can't be null
   */
  HxMethod setReturnType(HxType returnType);

  /**
   * @param returnType
   * @return
   * @implSpec return value can't be null
   */
  default HxMethod setReturnType(String returnType) {
    return setReturnType(getHaxxor().reference(returnType));
  }

  /**
   * @return default value of this annotation attribute
   */
  Object getDefaultValue();

  /**
   * @param value is a new default value for this annotation attribute
   * @return this
   */
  HxMethod setDefaultValue(Object value);

  /**
   * @return <b>true</b> if this method was declared by an annotation, represents an annotation's attribute and has a
   * default value, <b>false</b> otherwise.
   */
  default boolean hasDefaultValue() {
    return isAnnotationAttribute() && getDefaultValue() != null;
  }

  /**
   * @return
   */
  default HxGenericElement getGenericReturnType() {
    return getReturnType();
  }

  /**
   * @return <b>true</b> if this method is an annotation's attribute, <b>false</b> otherwise.
   */
  default boolean isAnnotationAttribute() {
    HxType type = getDeclaringMember();
    if (type == null || !type.isAnnotation()) {
      return false;
    }
    return
        isPublic() &&
        isAbstract() &&
        getParametersCount() == 0 &&
        !hasReturnType("void") &&
        !"hashCode".equals(getName()) &&
        !"toString".equals(getName()) &&
        !"annotationType".equals(getName());
  }

  /**
   * @return
   */
  default boolean isStatic() {
    return hasModifiers(Modifiers.STATIC);
  }

  /**
   * @return
   */
  default boolean isAbstract() {
    return hasModifiers(Modifiers.ABSTRACT);
  }

  /**
   * @return
   */
  default boolean isSynchronized() {
    return hasModifiers(Modifiers.SYNCHRONIZED);
  }

  /**
   * @return
   */
  default boolean isBrindge() {
    return hasModifiers(Modifiers.BRIDGE);
  }

  /**
   * @return
   */
  default boolean isNative() {
    return hasModifiers(Modifiers.NATIVE);
  }

  /**
   * @return
   */
  default boolean isFinal() {
    return hasModifiers(Modifiers.FINAL);
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
    SYNCHRONIZED(0x0020),
    // method
    BRIDGE(0x0040),
    // method
    VARARGS(0x0080),
    // method
    NATIVE(0x0100),
    // method
    ABSTRACT(0x0400),
    // class, method
    STRICT(0x0800),
    // method
    SYNTHETIC(0x1000); // class, field, method, parameter

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
    public static Set<Modifiers> toSet(int modifiers) {
      return HxModifier.toSet(Modifiers.class, modifiers);
    }

    @Override
    public int toBit() {
      return bit;
    }
  }
}
