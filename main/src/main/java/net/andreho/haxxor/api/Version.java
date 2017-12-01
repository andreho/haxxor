package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

/**
 */
public enum Version {
  V1_5(Opcodes.V1_5),
  V1_6(Opcodes.V1_6),
  V1_7(Opcodes.V1_7),
  V1_8(Opcodes.V1_8);

  final int code;

  public static Version of(int ver) {
    switch (ver) {
      case Opcodes.V1_5:
        return V1_5;
      case Opcodes.V1_6:
        return V1_6;
      case Opcodes.V1_7:
        return V1_7;
      case Opcodes.V1_8:
        return V1_8;
    }
    throw new IllegalArgumentException("Unsupported version: " + ver);
  }

  Version(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
