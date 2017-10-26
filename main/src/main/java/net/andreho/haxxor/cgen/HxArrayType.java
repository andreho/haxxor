package net.andreho.haxxor.cgen;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.api.HxSort;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 07:47.
 */
public enum HxArrayType {
  BOOLEAN(Opcodes.T_BOOLEAN, "[Z"),
  CHAR(Opcodes.T_CHAR, "[C"),
  FLOAT(Opcodes.T_FLOAT, "[F"),
  DOUBLE(Opcodes.T_DOUBLE, "[D"),
  BYTE(Opcodes.T_BYTE, "[B"),
  SHORT(Opcodes.T_SHORT, "[S"),
  INT(Opcodes.T_INT, "[I"),
  LONG(Opcodes.T_LONG, "[J");

  final int code;
  final String className;
  final Object stackOperand;

  HxArrayType(int code,
              String className) {
    this.code = code;
    this.className = className;
    this.stackOperand = className;
  }

  public int getCode() {
    return code;
  }

  public String getClassName() {
    return className;
  }

  public Object getStackOperand() {
    return this.stackOperand;
  }

  public static HxArrayType fromCode(int code) {
    switch (code) {
      case Opcodes.T_BOOLEAN:
        return BOOLEAN;
      case Opcodes.T_CHAR:
        return CHAR;
      case Opcodes.T_FLOAT:
        return FLOAT;
      case Opcodes.T_DOUBLE:
        return DOUBLE;
      case Opcodes.T_BYTE:
        return BYTE;
      case Opcodes.T_SHORT:
        return SHORT;
      case Opcodes.T_INT:
        return INT;
      case Opcodes.T_LONG:
        return LONG;
    }
    throw new IllegalArgumentException("Invalid array type code: " + code);
  }

  public static HxArrayType fromName(String name) {
    switch (name) {
      case "[Z":
      case "boolean[]":
        return BOOLEAN;
      case "[C":
      case "char[]":
        return CHAR;
      case "[F":
      case "float[]":
        return FLOAT;
      case "[D":
      case "double[]":
        return DOUBLE;
      case "[B":
      case "byte[]":
        return BYTE;
      case "[S":
      case "short[]":
        return SHORT;
      case "[I":
      case "int[]":
        return INT;
      case "[J":
      case "long[]":
        return LONG;
    }
    throw new IllegalArgumentException("Invalid array type name: " + name);
  }

  public static HxArrayType fromSort(HxSort sort) {
    switch (sort) {
      case BOOLEAN: return BOOLEAN;
      case BYTE: return BYTE;
      case SHORT: return SHORT;
      case CHAR: return CHAR;
      case INT: return INT;
      case FLOAT: return FLOAT;
      case LONG: return LONG;
      case DOUBLE: return DOUBLE;
    }
    throw new IllegalArgumentException("Invalid component sort: " + sort);
  }
}
