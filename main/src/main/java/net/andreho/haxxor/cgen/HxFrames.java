package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.ClassReader;
import net.andreho.asm.org.objectweb.asm.Opcodes;

/**
 * A bit overworked version of original frame constants from {@link Opcodes}
 * <br/>Created by a.hofmann on 05.06.2017 at 07:47.
 */
public enum HxFrames {
  /**
   * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
   */
  NEW(Opcodes.F_NEW),

  /**
   * Represents a compressed frame with complete frame data.
   */
  FULL(Opcodes.F_FULL),

  /**
   * Represents a compressed frame where locals are the same as the locals in
   * the previous frame, except that additional 1-3 locals are defined, and
   * with an empty stack.
   */
  APPEND(Opcodes.F_APPEND),

  /**
   * Represents a compressed frame where locals are the same as the locals in
   * the previous frame, except that the last 1-3 locals are absent and with
   * an empty stack.
   */
  CHOP(Opcodes.F_CHOP),

  /**
   * Represents a compressed frame with exactly the same locals as the
   * previous frame and with an empty stack.
   */
  SAME(Opcodes.F_SAME),

  /**
   * Represents a compressed frame with exactly the same locals as the
   * previous frame and with a single value on the stack.
   */
  SAME1(Opcodes.F_SAME1);

  final int code;

  HxFrames(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static HxFrames fromCode(int code) {
    switch (code) {
      case Opcodes.F_NEW:
        return NEW;
      case Opcodes.F_FULL:
        return FULL;
      case Opcodes.F_APPEND:
        return APPEND;
      case Opcodes.F_CHOP:
        return CHOP;
      case Opcodes.F_SAME:
        return SAME;
      case Opcodes.F_SAME1:
        return SAME1;
    }
    throw new IllegalArgumentException("Invalid frame code: " + code);
  }
}
