package net.andreho.haxxor.spec.api;

import java.util.Set;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxConstructor
    extends HxExecutable<HxConstructor>,
            Cloneable {

  /**
   * @return a new copy of this constructor
   */
  HxConstructor clone();

  /**
   * Modifiers available for a constructor
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
    // method
    BRIDGE(0x0040),
    // method
    VARARGS(0x0080),
    // method
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
     * @return enum-set representation of given constructor's modifiers
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
