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

  /**
   * @param name is the binary classname of a type (like: int, java.lang.String or byte[])
   * @return
   */
  public static HxSort fromName(String name) {
    if (name.startsWith(DESC_ARRAY_PREFIX_STR) ||
        name.endsWith(ARRAY_DIMENSION)) {
      return ARRAY;
    }
    if (name.length() > "boolean".length()) {
      return OBJECT;
    }
    switch (name.charAt(0)) {
      case 'b':
        if("boolean".equals(name)) {
          return BOOLEAN;
        }
        if("byte".equals(name)) {
          return BYTE;
        }
        break;
      case 'c':
        if("char".equals(name)) {
          return CHAR;
        }
        break;
      case 'd':
        if("double".equals(name)) {
          return DOUBLE;
        }
        break;
      case 'i':
        if("int".equals(name)) {
          return INT;
        }
        break;
      case 'f':
        if("float".equals(name)) {
          return FLOAT;
        }
        break;
      case 'l':
        if("long".equals(name)) {
          return LONG;
        }
        break;
      case 'v':
        if("void".equals(name)) {
          return VOID;
        }
        break;
      case 's':
        if("short".equals(name)) {
          return SHORT;
        }
        break;
    }
    return OBJECT;
  }

  public static HxSort fromDescriptor(String desc) {
    if (desc.startsWith(DESC_ARRAY_PREFIX_STR)) {
      return ARRAY;
    }
    if (desc.length() != 1) {
      return OBJECT;
    }
    switch (desc.charAt(0)) {
      case 'V': return VOID;
      case 'Z': return BOOLEAN;
      case 'B': return BYTE;
      case 'C': return CHAR;
      case 'D': return DOUBLE;
      case 'I': return INT;
      case 'F': return FLOAT;
      case 'J': return LONG;
      case 'S': return SHORT;
      case 'L': return OBJECT;
      default:
        throw new IllegalArgumentException("Not a descriptor: "+desc);
    }
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
