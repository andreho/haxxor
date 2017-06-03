package net.andreho.haxxor.spec.api;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMethod
    extends HxParameterizable<HxMethod>,
            Cloneable {

  /**
   * @return name of this method
   */
  String getName();

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
   * @return <b>true</b> if this method was declared by an annotation, represents an annotation's attribute and has a
   * default value, <b>false</b> otherwise.
   */
  default boolean hasDefaultValue() {
    HxType type = getDeclaringMember();
    if(type == null || !type.isAnnotation()) {
      return false;
    }
    if(getArity() > 0) {
      return false;
    }
    return getDefaultValue() != null;
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
   * @return
   */
  default HxGeneric getGenericReturnType() {
    return getReturnType();
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

    @Override
    public int toBit() {
      return bit;
    }
  }
}
