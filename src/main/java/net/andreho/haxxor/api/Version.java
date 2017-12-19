package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Opcodes;

/**
 */
public enum Version {
  V1_1(Opcodes.V1_1),
  V1_2(Opcodes.V1_2),
  V1_3(Opcodes.V1_3),
  V1_4(Opcodes.V1_4),
  V1_5(Opcodes.V1_5),
  V1_6(Opcodes.V1_6),
  V1_7(Opcodes.V1_7),
  V1_8(Opcodes.V1_8);

  final int code;

  /**
   * @param ver of a classfile
   * @return
   */
  public static Version of(int ver) {
    switch (ver) {
      case Opcodes.V1_1:
        return V1_1;
      case Opcodes.V1_2:
        return V1_2;
      case Opcodes.V1_3:
        return V1_3;
      case Opcodes.V1_4:
        return V1_4;
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

  public int toInteger() {
    return code;
  }
}
