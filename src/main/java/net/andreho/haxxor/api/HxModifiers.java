package net.andreho.haxxor.api;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public enum HxModifiers
    implements HxModifier {

  PUBLIC(0x0001),
  PRIVATE(0x0002),
  PROTECTED(0x0004),
  STATIC(0x0008),
  FINAL(0x0010),

  SUPER(0x0020), //Overlaps with SYNCHRONIZED
  SYNCHRONIZED(0x0020), //Overlaps with SUPER

  VOLATILE(0x0040), //Overlaps with BRIDGE
  BRIDGE(0x0040), //Overlaps with VOLATILE

  VARARGS(0x0080), //Overlaps with TRANSIENT
  TRANSIENT(0x0080), //Overlaps with VARARGS

  NATIVE(0x0100),
  INTERFACE(0x0200),
  ABSTRACT(0x0400),
  STRICT(0x0800),
  SYNTHETIC(0x1000),
  ANNOTATION(0x2000),
  ENUM(0x4000),
  MANDATED(0x8000),

  DEPRECATED(0x20000);

  private final int bit;

  HxModifiers(int bit) {
    this.bit = bit;
  }

  @Override
  public int toBit() {
    return bit;
  }

  /**
   * Transforms given bit-set to an {@link EnumSet} with appropriate type
   *
   * @param modifiers to transform
   * @return a set with extracted modifiers
   */
  /**
   * Transforms given modifiers to an equal enum-set
   *
   * @param modifiers to transform
   * @return enum-set representation of given field's modifiers
   */
  public static Set<HxModifiers> toSet(int modifiers) {
    return HxModifier.toSet(HxModifiers.class, modifiers);
  }
}
