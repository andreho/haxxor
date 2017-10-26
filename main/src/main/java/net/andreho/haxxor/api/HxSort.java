package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:17.
 */
public enum HxSort {
  VOID("void"),

  BOOLEAN("boolean", "java/lang/Boolean", "(Z)Ljava/lang/Boolean;", "booleanValue", "()Z"),
  BYTE("byte", "java/lang/Byte", "(B)Ljava/lang/Byte;", "byteValue", "()B", "short", "int", "long", "float", "double"),
  SHORT("short", "java/lang/Short", "(S)Ljava/lang/Short;", "shortValue", "()S", "int", "long", "float", "double"),
  CHAR("char", "java/lang/Character", "(C)Ljava/lang/Character;", "charValue", "()C", "int", "long", "float", "double"),
  INT("int", "java/lang/Integer", "(I)Ljava/lang/Integer;", "intValue", "()I", "long", "float", "double"),
  FLOAT("float", "java/lang/Float", "(F)Ljava/lang/Float;", "floatValue", "()F", "double"),
  LONG("long", "java/lang/Long", "(J)Ljava/lang/Long;", "longValue", "()J", "float", "double"),
  DOUBLE("double", "java/lang/Double", "(D)Ljava/lang/Double;", "doubleValue", "()D"),

  OBJECT(""),
  ARRAY("");

  private final String name;
  private final String wrapper;
  private final String descriptor;
  private final String wrapperMethodSignature;
  private final String toPrimitiveMethod;
  private final String toPrimitiveMethodSignature;
  private final List<String> widening;

  public static HxSort fromName(String name) {
    if (name.indexOf('[') > -1) {
      return ARRAY;
    }

    if (name.length() > "boolean".length()) {
      return OBJECT;
    }

    switch (name) {
      case "void":
        return VOID;
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

    return OBJECT;
  }

  HxSort(final String name) {
    this(name, "", "", "", "");
  }

  HxSort(final String name,
         final String wrapper,
         final String wrapperMethodSignature,
         final String toPrimitiveMethod,
         final String toPrimitiveMethodSignature,
         final String... widening) {
    this.name = name;
    this.wrapper = wrapper;
    this.wrapperMethodSignature = wrapperMethodSignature;
    this.toPrimitiveMethod = toPrimitiveMethod;
    this.toPrimitiveMethodSignature = toPrimitiveMethodSignature;
    this.descriptor = wrapper.isEmpty() ? "" : "L" + wrapper + ";";
    this.widening = Collections.unmodifiableList(Arrays.asList(widening));
  }

  public boolean isObject() {
    return OBJECT == this;
  }

  public boolean isArray() {
    return ARRAY == this;
  }

  public boolean isVoid() {
    return VOID == this;
  }

  public boolean isPrimitive() {
    return !isObject() && !isArray();
  }

  public String getWrapper() {
    return wrapper;
  }

  public String getDescriptor() {
    return descriptor;
  }

  public List<String> getWidening() {
    return widening;
  }

  /**
   * @param name of primitive type
   * @return <b>-1</b> if not supported or some sort widening distance in range from 0 to 5
   */
  public int wideningDistance(String name) {
    if(isPrimitive() && this.name.equals(name)) {
      return 0;
    }
    int index = getWidening().indexOf(name);
    return index > -1 ? 1 + index : -1;
  }

  /**
   * @param sort of possible primitive type
   * @return <b>-1</b> if not supported or some sort widening distance in range from 0 to 5
   */
  public int wideningDistance(HxSort sort) {
    return wideningDistance(sort.name);
  }

  @Override
  public String toString() {
    return name;
  }
}
