package net.andreho.haxxor.spec.api;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:17.
 */
public enum HxSort {
  VOID("void"),
  BOOLEAN("boolean"),
  BYTE("byte"),
  SHORT("short"),
  CHAR("char"),
  INT("int"),
  FLOAT("float"),
  LONG("long"),
  DOUBLE("double"),
  OBJECT("object"),
  ARRAY("");

  private final String name;

  HxSort(final String name) {
    this.name = name;
  }

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

  @Override
  public String toString() {
    return name;
  }
}
