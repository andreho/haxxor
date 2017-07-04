package net.andreho.haxxor.utils;

import static net.andreho.haxxor.spec.api.HxConstants.ARRAY_DIMENSION;
import static net.andreho.haxxor.spec.api.HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR;
import static net.andreho.haxxor.spec.api.HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 08:36.
 */
public abstract class NamingUtils {

  private static final int VOID = 0;
  private static final int BOOLEAN = 1;
  private static final int BYTE = 2;
  private static final int SHORT = 3;
  private static final int CHAR = 4;
  private static final int INT = 5;
  private static final int FLOAT = 6;
  private static final int LONG = 7;
  private static final int DOUBLE = 8;
  private static final int ARRAY = 9;
  private static final int OBJECT = 10;

  private NamingUtils() {
  }

  private static int simpleNameLength(final String className) {
    int length = className.length() - getDimension(className) * ARRAY_DIMENSION.length();
    int offset = className.lastIndexOf('$');
    if (offset < 0) {
      return length - className.lastIndexOf('.');
    }
    length -= offset;
    if (length < 1 || className.charAt(offset) != '$') {
      throw new InternalError("Malformed class name");
    }
    offset += 1;
    while (offset < length && isDigit(className.charAt(offset))) {
      offset++;
    }
    return length - offset;
  }

  /**
   * @param classname
   * @return
   */
  public static boolean isPrimitive(String classname) {
    switch (classname.charAt(0)) {
      case 'b':
        return "byte".equals(classname) || "boolean".equals(classname);
      case 's':
        return "short".equals(classname);
      case 'c':
        return "char".equals(classname);
      case 'i':
        return "int".equals(classname);
      case 'f':
        return "float".equals(classname);
      case 'l':
        return "long".equals(classname);
      case 'd':
        return "double".equals(classname);
      case 'v':
        return "void".equals(classname);
      default:
        return false;
    }
  }

  /**
   * @param classname
   * @return
   */
  private static int toElementCode(String classname, int offset, int length) {
    switch (classname.charAt(offset)) {
      case 'b': {
        if (classname.startsWith("byte", offset)) {
          return BYTE;
        }
        if (classname.startsWith("boolean", offset)) {
          return BOOLEAN;
        }
      }
      break;
      case 's': {
        if (classname.startsWith("short", offset)) {
          return SHORT;
        }
      }
      break;
      case 'c': {
        if (classname.startsWith("char", offset)) {
          return CHAR;
        }
      }
      break;
      case 'i': {
        if (classname.startsWith("int", offset)) {
          return INT;
        }
      }
      break;
      case 'f': {
        if (classname.startsWith("float", offset)) {
          return FLOAT;
        }
      }
      break;
      case 'l': {
        if (classname.startsWith("long", offset)) {
          return LONG;
        }
      }
      break;
      case 'd': {
        if (classname.startsWith("double", offset)) {
          return DOUBLE;
        }
      }
      break;
      case 'v': {
        if (classname.startsWith("void", offset)) {
          return VOID;
        }
      }
      break;
    }
    if(classname.startsWith(ARRAY_DIMENSION, length - 2)) {
      return ARRAY;
    }
    return OBJECT;
  }

  /**
   * @param value
   * @return
   */
  public static boolean isDescriptor(String value) {
    return isDescriptor(value, 0, value.length());
  }

  /**
   * @param value
   * @param offset
   * @param length
   * @return
   */
  public static boolean isDescriptor(String value,
                                     int offset,
                                     int length) {
    char c = value.charAt(offset);
    switch (c) {
      case 'Z':
      case 'B':
      case 'S':
      case 'C':
      case 'I':
      case 'F':
      case 'J':
      case 'D':
        return true;
      case '[':
        return isDescriptor(value, offset + 1, length);
      case 'L': {
        int semiColumn = value.indexOf(';', offset + 1);
        return inRange(offset + 2, length + 1, semiColumn) &&
               hasNot(value, offset + 2, semiColumn, '.');
      }
    }
    return false;
  }

  private static boolean inRange(int from,
                                 int to,
                                 int index) {
    return index >= from && index < to;
  }

  private static boolean hasNot(String value,
                                int offset,
                                int length,
                                char invalidChar) {
    int index = value.indexOf(invalidChar, offset);
    return index < 0 || index >= length;
  }

  /**
   * @param classname
   * @return
   */
  public static String toInternalClassname(String classname) {
    if (isDescriptor(classname) ||
        isPrimitive(classname)) {
      return classname;
    }
    return classname.replace(JAVA_PACKAGE_SEPARATOR_CHAR, INTERNAL_PACKAGE_SEPARATOR_CHAR);
  }

  /**
   * @param classname
   * @return
   */
  public static String toDescriptor(String classname) {
    return toDescriptor(classname, 0, classname.length());
  }

  /**
   * @param classname
   * @return
   */
  public static String toDescriptor(String classname, int offset, int length) {
    if(isDescriptor(classname, offset, length)) {
      return classname.substring(offset, length);
    }
    boolean array = false;
    switch (toElementCode(classname, offset, length)) {
      case VOID: return "V";
      case BOOLEAN: return "Z";
      case BYTE: return "B";
      case SHORT: return "S";
      case CHAR: return "C";
      case INT: return "I";
      case FLOAT: return "F";
      case LONG: return "J";
      case DOUBLE: return "D";
      case ARRAY: array = true; break;
    }
//    return classname.replace(JAVA_PACKAGE_SEPARATOR_CHAR, INTERNAL_PACKAGE_SEPARATOR_CHAR);
    throw new UnsupportedOperationException();
  }

  /**
   * @param className
   * @return
   */
  public static boolean isAnonymous(String className) {
    return simpleNameLength(className) == 0;
  }

  /**
   * @param className
   * @return
   */
  public static boolean hasSimpleName(String className) {
    return simpleNameLength(className) > 0;
  }

  /**
   * @param className
   * @return
   */
  public static boolean hasSimpleBinaryName(String className) {
    return className.lastIndexOf('$') > -1;
  }

  /**
   * @param className
   * @return
   */
  public static boolean isArray(String className) {
    return className.endsWith(ARRAY_DIMENSION);
  }

  /**
   * @param className
   * @return
   */
  public static int getDimension(String className) {
    int dims = 0;
    int offset = className.length() - ARRAY_DIMENSION.length();
    while (className.startsWith(ARRAY_DIMENSION, offset)) {
      offset -= ARRAY_DIMENSION.length();
      dims++;
    }
    return dims;
  }

  /**
   * @param className is the fully-qualified classname
   * @return <b>null</b> if top element or minimal simple-name of the referenced class
   */
  public static String toSimpleName(String className) {
    int dims = getDimension(className);
    if (dims > 0) {
      final String componentClassname =
        className.substring(0, className.length() - dims * ARRAY_DIMENSION.length());
      final StringBuilder builder = new StringBuilder(toSimpleName(componentClassname));

      for (int i = 0; i < dims; i++) {
        builder.append(ARRAY_DIMENSION);
      }
      return builder.toString();
    }

    String simpleName = toSimpleBinaryName(className);
    if (simpleName == null) {
      // top level class
      simpleName = className;
      int dotIndex = simpleName.lastIndexOf(".");
      return dotIndex < 0 ?
             simpleName :
             simpleName.substring(dotIndex + 1); // strip the package name
    }

    // Remove leading "\$[0-9]*" from the name
    int length = simpleName.length();
    if (length < 1 || simpleName.charAt(0) != '$') {
      throw new InternalError("Malformed class name");
    }
    int index = 1;
    while (index < length && isDigit(simpleName.charAt(index))) {
      index++;
    }
    // Eventually, this is the empty string if this is an anonymous class
    return simpleName.substring(index);
  }

  private static boolean isDigit(final char c) {
    return c >= '0' && c <= '9';
  }

  /**
   * @param className is the fully-qualified classname
   * @return <b>null</b> if top element or minimal simple-name of the referenced class
   */
  public static String toSimpleBinaryName(String className) {
    int dollarIndex = className.lastIndexOf('$');
    if (dollarIndex < 0) {
      return null;
    }
    return className.substring(dollarIndex);
  }
}
