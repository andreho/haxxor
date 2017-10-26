package net.andreho.haxxor.api;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public interface HxModifier {

  /**
   * Transforms given modifiers to raw bit-set representation
   *
   * @param collection of modifiers to merge
   * @return corresponding modifier bit-set
   */
  static int toBits(Collection<? extends HxModifier> collection) {
    int bits = 0;

    for (HxModifier modifier : collection) {
      bits |= modifier.toBit();
    }

    return bits;
  }

  /**
   * Transforms given modifiers to raw bit-set representation
   *
   * @param modifiers to merge
   * @return corresponding modifier bit-set
   */
  static int toBits(HxModifier... modifiers) {
    int bits = 0;

    for (HxModifier modifier : modifiers) {
      bits |= modifier.toBit();
    }

    return bits;
  }

  /**
   * Calculates a set representation of given modifier's bitset
   *
   * @param enumClass of corresponding modifier's enum
   * @param modifiers to evaluate
   * @param <E>
   * @return a set with all given modifiers
   * @throws IllegalArgumentException if given modifier is invalid and can't be represented using given enum-type
   */
  static <E extends Enum<E> & HxModifier> Set<E> toSet(Class<E> enumClass,
                                                       int modifiers) {
    final int ACC_PUBLIC = 0x0001; // class, field, method
    final int ACC_PRIVATE = 0x0002; // class, field, method
    final int ACC_PROTECTED = 0x0004; // class, field, method
    final int ACC_STATIC = 0x0008; // field, method
    final int ACC_FINAL = 0x0010; // class, field, method, parameter
    final int ACC_SUPER = 0x0020; // class
    final int ACC_SYNCHRONIZED = 0x0020; // method
    final int ACC_VOLATILE = 0x0040; // field
    final int ACC_BRIDGE = 0x0040; // method
    final int ACC_VARARGS = 0x0080; // method
    final int ACC_TRANSIENT = 0x0080; // field
    final int ACC_NATIVE = 0x0100; // method
    final int ACC_INTERFACE = 0x0200; // class
    final int ACC_ABSTRACT = 0x0400; // class, method
    final int ACC_STRICT = 0x0800; // method
    final int ACC_SYNTHETIC = 0x1000; // class, field, method, parameter
    final int ACC_ANNOTATION = 0x2000; // class
    final int ACC_ENUM = 0x4000; // class(?) field inner
    final int ACC_MANDATED = 0x8000; // parameter

    final Set<E> set = EnumSet.noneOf(enumClass);

    if ((modifiers & ACC_PUBLIC) != 0) {
      set.add(Enum.valueOf(enumClass, "PUBLIC"));
    }
    if ((modifiers & ACC_PROTECTED) != 0) {
      set.add(Enum.valueOf(enumClass, "PROTECTED"));
    }
    if ((modifiers & ACC_PRIVATE) != 0) {
      set.add(Enum.valueOf(enumClass, "PRIVATE"));
    }
    if ((modifiers & ACC_STATIC) != 0) {
      set.add(Enum.valueOf(enumClass, "STATIC"));
    }
    if ((modifiers & ACC_FINAL) != 0) {
      set.add(Enum.valueOf(enumClass, "FINAL"));
    }

    if(enumClass.getEnclosingClass() == HxType.class) {
      if ((modifiers & ACC_SUPER) != 0) {
        set.add(Enum.valueOf(enumClass, "SUPER"));
      }
    } else if ((modifiers & ACC_SYNCHRONIZED) != 0) {
      set.add(Enum.valueOf(enumClass, "SYNCHRONIZED"));
    }

    if(enumClass.getEnclosingClass() == HxField.class) {
      if ((modifiers & ACC_VOLATILE) != 0) {
        set.add(Enum.valueOf(enumClass, "VOLATILE"));
      }
      if ((modifiers & ACC_TRANSIENT) != 0) {
        set.add(Enum.valueOf(enumClass, "TRANSIENT"));
      }
    } else {
      if ((modifiers & ACC_BRIDGE) != 0) {
        set.add(Enum.valueOf(enumClass, "BRIDGE"));
      }
      if ((modifiers & ACC_VARARGS) != 0) {
        set.add(Enum.valueOf(enumClass, "VARARGS"));
      }
    }

    if ((modifiers & ACC_NATIVE) != 0) {
      set.add(Enum.valueOf(enumClass, "NATIVE"));
    }
    if ((modifiers & ACC_INTERFACE) != 0) {
      set.add(Enum.valueOf(enumClass, "INTERFACE"));
    }
    if ((modifiers & ACC_ABSTRACT) != 0) {
      set.add(Enum.valueOf(enumClass, "ABSTRACT"));
    }
    if ((modifiers & ACC_STRICT) != 0) {
      set.add(Enum.valueOf(enumClass, "STRICT"));
    }
    if ((modifiers & ACC_SYNTHETIC) != 0) {
      set.add(Enum.valueOf(enumClass, "SYNTHETIC"));
    }
    if ((modifiers & ACC_ANNOTATION) != 0) {
      set.add(Enum.valueOf(enumClass, "ANNOTATION"));
    }
    if ((modifiers & ACC_ENUM) != 0) {
      set.add(Enum.valueOf(enumClass, "ENUM"));
    }
    if ((modifiers & ACC_MANDATED) != 0) {
      set.add(Enum.valueOf(enumClass, "MANDATED"));
    }
    return set;
  }

  /**
   * @return an integer with a set bit that corresponds to this modifier
   */
  int toBit();
}
