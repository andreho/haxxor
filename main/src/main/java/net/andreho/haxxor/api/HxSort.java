package net.andreho.haxxor.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.andreho.haxxor.api.HxConstants.ARRAY_DIMENSION;
import static net.andreho.haxxor.api.HxConstants.DESC_ARRAY_PREFIX_STR;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 03:17.
 */
public enum HxSort {
  VOID("void", "V", Void.TYPE, Void.class),

  BOOLEAN("boolean", "Z", "java/lang/Boolean", "(Z)Ljava/lang/Boolean;", "booleanValue", "()Z",
          Boolean.TYPE, Boolean.class),
  BYTE("byte", "B", "java/lang/Byte", "(B)Ljava/lang/Byte;", "byteValue", "()B",
       Byte.TYPE, Byte.class, "short", "int", "long", "float", "double"),
  SHORT("short", "S", "java/lang/Short", "(S)Ljava/lang/Short;", "shortValue", "()S",
        Short.TYPE, Short.class, "int", "long", "float", "double"),
  CHAR("char", "C", "java/lang/Character", "(C)Ljava/lang/Character;", "charValue", "()C",
       Character.TYPE, Character.class, "int", "long", "float", "double"),
  INT("int", "I", "java/lang/Integer", "(I)Ljava/lang/Integer;", "intValue", "()I",
      Integer.TYPE, Integer.class, "long", "float", "double"),
  FLOAT("float", "F", "java/lang/Float", "(F)Ljava/lang/Float;", "floatValue", "()F",
        Float.TYPE, Float.class, "double"),
  LONG("long", "J", "java/lang/Long", "(J)Ljava/lang/Long;", "longValue", "()J",
       Long.TYPE, Long.class, "float", "double"),
  DOUBLE("double", "D", "java/lang/Double", "(D)Ljava/lang/Double;", "doubleValue", "()D",
         Double.TYPE, Double.class),

  OBJECT(""),
  ARRAY("");

  private final String name;
  private final String wrapper;
  private final String descriptor;
  private final String wrapperDescriptor;
  private final String wrapperMethodSignature;
  private final String toPrimitiveMethod;
  private final String toPrimitiveMethodSignature;
  private final List<String> widening;
  private final Class<?> primitiveClass;
  private final Class<?> wrapperClass;

  public static HxSort fromName(String name) {
    if (name.startsWith(DESC_ARRAY_PREFIX_STR) ||
        name.endsWith(ARRAY_DIMENSION)) {
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
    this(name, "", "", "", "", "", null, null);
  }

  HxSort(final String name,
         final String descriptor,
         final Class<?> primitiveClass,
         final Class<?> wrapperClass) {
    this(name, descriptor, "", "", "", "", primitiveClass, wrapperClass);
  }

  HxSort(final String name,
         final String descriptor,
         final String wrapper,
         final String wrapperMethodSignature,
         final String toPrimitiveMethod,
         final String toPrimitiveMethodSignature,
         final Class<?> primitiveClass,
         final Class<?> wrapperClass,
         final String... widening) {
    this.name = name;
    this.wrapper = wrapper;
    this.descriptor = descriptor;
    this.wrapperMethodSignature = wrapperMethodSignature;
    this.toPrimitiveMethod = toPrimitiveMethod;
    this.toPrimitiveMethodSignature = toPrimitiveMethodSignature;
    this.wrapperDescriptor = wrapper.isEmpty() ? "" : "L" + wrapper + ";";
    this.primitiveClass = primitiveClass;
    this.wrapperClass = wrapperClass;
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

  public String getWrapperDescriptor() {
    return wrapperDescriptor;
  }

  public String getWrapperMethodSignature() {
    return wrapperMethodSignature;
  }

  public Class<?> getPrimitiveClass() {
    return primitiveClass;
  }

  public Class<?> getWrapperClass() {
    return wrapperClass;
  }

  public List<String> getWidening() {
    return widening;
  }

  /**
   * @param name of primitive type
   * @return <b>-1</b> if not supported or some sort widening distance in range from 0 to 5
   */
  public int wideningDistance(String name) {
    if (isPrimitive() && this.name.equals(name)) {
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
