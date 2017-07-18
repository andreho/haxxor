package net.andreho.args;

/**
 * <br/>Created by a.hofmann on 15.07.2017 at 05:24.
 */
public enum ArgumentsType {
  BOOLEAN('Z', "boolean"),
  BYTE('B', "byte"),
  SHORT('S', "short"),
  CHAR('C', "char"),
  INT('I', "int"),
  FLOAT('F', "float"),
  LONG('J', "long"),
  DOUBLE('D', "double"),
  OBJECT('O', "java.lang.Object");

  private final char code;
  private final String fieldType;

  public static ArgumentsType fromClass(Class<?> cls) {
    if (cls.isPrimitive()) {
      switch (cls.getName()) {
        case "boolean":
          return BOOLEAN;
        case "byte":
          return BYTE;
        case "short":
          return SHORT;
        case "char":
          return CHAR;
        case "int":
          return INT;
        case "float":
          return FLOAT;
        case "long":
          return LONG;
        case "double":
          return DOUBLE;
      }
      throw new IllegalArgumentException("Unsupported type: " + cls.getName());
    }
    return OBJECT;
  }

  public static ArgumentsType fromCharCode(char code) {
    switch (code) {
      case 'Z':
        return BOOLEAN;
      case 'B':
        return BYTE;
      case 'S':
        return SHORT;
      case 'C':
        return CHAR;
      case 'I':
        return INT;
      case 'F':
        return FLOAT;
      case 'J':
        return LONG;
      case 'D':
        return DOUBLE;
      case 'O':
        return OBJECT;
    }
    throw new IllegalArgumentException("Unsupported argument's char-code: " + code);
  }

  public static ArgumentsType fromCode(int code) {
    switch (0xF & code) {
      case 0:
        return BOOLEAN;
      case 1:
        return BYTE;
      case 2:
        return SHORT;
      case 3:
        return CHAR;
      case 4:
        return INT;
      case 5:
        return FLOAT;
      case 6:
        return LONG;
      case 7:
        return DOUBLE;
      case 8:
        return OBJECT;
    }
    throw new IllegalArgumentException("Unsupported argument's code: " + code);
  }

  ArgumentsType(final char code,
                final String fieldType) {
    this.code = code;
    this.fieldType = fieldType;
  }

  public char getCode() {
    return code;
  }

  public String getFieldType() {
    return fieldType;
  }

  public boolean isPrimitive() {
    return this != OBJECT;
  }
}
