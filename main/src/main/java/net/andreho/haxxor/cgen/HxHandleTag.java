package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;

/**
 * <br/>Created by a.hofmann on 12.06.2017 at 01:58.
 */
public enum HxHandleTag {
  GETFIELD(Opcodes.H_GETFIELD),
  GETSTATIC(Opcodes.H_GETSTATIC),
  PUTFIELD(Opcodes.H_PUTFIELD),
  PUTSTATIC(Opcodes.H_PUTSTATIC),
  INVOKEVIRTUAL(Opcodes.H_INVOKEVIRTUAL),
  INVOKESTATIC(Opcodes.H_INVOKESTATIC),
  INVOKESPECIAL(Opcodes.H_INVOKESPECIAL),
  NEWINVOKESPECIAL(Opcodes.H_NEWINVOKESPECIAL),
  INVOKEINTERFACE(Opcodes.H_INVOKEINTERFACE);

  private final int code;

  HxHandleTag(final int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static HxHandleTag fromCode(int code) {
    switch (code) {
      case Opcodes.H_GETFIELD:
        return GETFIELD;
      case Opcodes.H_GETSTATIC:
        return GETSTATIC;
      case Opcodes.H_PUTFIELD:
        return PUTFIELD;
      case Opcodes.H_PUTSTATIC:
        return PUTSTATIC;
      case Opcodes.H_INVOKEVIRTUAL:
        return INVOKEVIRTUAL;
      case Opcodes.H_INVOKESTATIC:
        return INVOKESTATIC;
      case Opcodes.H_INVOKESPECIAL:
        return INVOKESPECIAL;
      case Opcodes.H_NEWINVOKESPECIAL:
        return NEWINVOKESPECIAL;
      case Opcodes.H_INVOKEINTERFACE:
        return INVOKEINTERFACE;
      default:
        throw new IllegalArgumentException("Unknown tag: " + code);
    }
  }
}
