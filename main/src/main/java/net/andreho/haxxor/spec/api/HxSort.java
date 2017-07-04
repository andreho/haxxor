package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:17.
 */
public enum HxSort {
  VOID,
  BOOLEAN,
  BYTE,
  SHORT,
  CHAR,
  INT,
  FLOAT,
  LONG,
  DOUBLE,
  OBJECT,
  ARRAY;

  public static HxSort fromName(String name) {
    if(name.indexOf('[') > -1) {
      return ARRAY;
    }

    if(name.length() > "boolean".length()) {
      return OBJECT;
    }

    switch (name) {
      case "void": return VOID;
      case "boolean": return BOOLEAN;
      case "byte": return BYTE;
      case "short": return SHORT;
      case "char": return CHAR;
      case "int": return INT;
      case "float": return FLOAT;
      case "long": return LONG;
      case "double": return DOUBLE;
    }

    return OBJECT;
  }

  public boolean isObject() {
    return OBJECT == this;
  }

  public boolean isArray() {
    return ARRAY == this;
  }

  public boolean isPrimitive() {
    return !isObject() && !isArray();
  }
}
