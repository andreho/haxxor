package net.andreho.haxxor.utils;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.api.HxConstants;

import java.util.Objects;

import static net.andreho.haxxor.api.HxConstants.ARRAY_DIMENSION;
import static net.andreho.haxxor.api.HxConstants.DESC_ARRAY_PREFIX;
import static net.andreho.haxxor.api.HxConstants.DESC_ARRAY_PREFIX_STR;
import static net.andreho.haxxor.api.HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR;
import static net.andreho.haxxor.api.HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR;

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

  public static String toPrimitiveClassname(char c) {
    switch (c) {
      case 'V':
        return "void";
      case 'Z':
        return "boolean";
      case 'B':
        return "byte";
      case 'S':
        return "short";
      case 'C':
        return "char";
      case 'I':
        return "int";
      case 'F':
        return "float";
      case 'J':
        return "long";
      case 'D':
        return "double";
      default:
        throw new IllegalArgumentException("Not a primitive literal {V,Z,B,S,C,I,F,J,D}: " + c);
    }
  }

  /**
   * Transforms if needed the given descriptor to a normalized classname of corresponding primitive type
   * @param desc to check and transform
   * @return possibly transformed classname or the same descriptor if not a primitive
   */
  public static String toPrimitiveClassname(String desc) {
    //Both examples like: [I or LI; have length over one.
    if(desc.length() != 1) {
      return desc;
    }
    return toPrimitiveClassname(desc, 0);
  }

  /**
   * Transforms the given descriptor to a normalized classname of corresponding primitive type
   * @param desc to check and transform
   * @param index to look at
   * @return transformed classname of the given primitive type at the given index
   */
  public static String toPrimitiveClassname(String desc, int index) {
    return toPrimitiveClassname(desc.charAt(index));
  }

  /**
   * @param classname
   * @return
   */
  public static String normalizeClassname(String classname) {
    Objects.requireNonNull(classname, "Classname can't be null.");
    String internalForm = classname.replace(HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR, HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR);
    if(classname.endsWith(";")) {
      return normalizeType(Type.getType(internalForm));
    }
    return normalizeType(Type.getObjectType(internalForm));
  }

  /**
   * @param signature
   * @return
   */
  public static String[] normalizeSignature(String signature) {
    Type[] types = Type.getArgumentTypes(signature);
    String[] names = new String[types.length];
    for (int i = 0; i < types.length; i++) {
      names[i] = normalizeType(types[i]);
    }
    return names;
  }

  /**
   * @param signature
   * @return
   */
  public static String normalizeReturnType(final String signature) {
    return normalizeType(Type.getReturnType(signature));
  }

  private static String normalizeType(final Type type) {
    return type.getClassName();
  }

  private static int simpleNameLength(final String classname) {
    int length = classname.length() - getDimension(classname) * ARRAY_DIMENSION.length();
    int offset = classname.lastIndexOf('$');
    if (offset < 0) {
      return length - classname.lastIndexOf('.');
    }
    length -= offset;
    if (length < 1 || classname.charAt(offset) != '$') {
      throw new InternalError("Malformed class name");
    }
    offset += 1;
    while (offset < length && isDigit(classname.charAt(offset))) {
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
   * @param value to check
   * @param offset in the value
   * @param length of the value
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
        return inRange(offset + 2, length + 1, semiColumn);
               //&& hasNot(value, offset + 2, semiColumn, '.');
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
   * @param cls whose name should be converted to an internal classname
   * @return an internal classname
   */
  public static String toInternalClassname(Class<?> cls) {
    return toInternalClassname(cls.getName());
  }

  /**
   * @param classname
   * @return
   */
  public static String toInternalClassname(String classname) {
    if (isPrimitive(classname)) {
      return classname;
    }
    if (isDescriptor(classname)) {
      return classname;
    }
    return classname.replace(JAVA_PACKAGE_SEPARATOR_CHAR, INTERNAL_PACKAGE_SEPARATOR_CHAR);
  }

  /**
   * @param returnType
   * @param signature
   * @return
   */
  public static String toDescriptor(Class<?> returnType, Class<?> ... signature) {
    final StringBuilder builder = new StringBuilder().append('(');
    for(Class<?> cls : signature) {
      toDescriptor(cls, builder);
    }
    return toDescriptor(returnType, builder.append(')')).toString();
  }

  private static StringBuilder toDescriptor(Class<?> cls, StringBuilder builder) {
    if(cls.isPrimitive()) {
      if(cls == Integer.TYPE) {
        return builder.append("I");
      }
      if(cls == Double.TYPE) {
        return builder.append("D");
      }
      if(cls == Long.TYPE) {
        return builder.append("J");
      }
      if(cls == Float.TYPE) {
        return builder.append("F");
      }
      if(cls == Boolean.TYPE) {
        return builder.append("Z");
      }
      if(cls == Byte.TYPE) {
        return builder.append("B");
      }
      if(cls == Character.TYPE) {
        return builder.append("C");
      }
      if(cls == Short.TYPE) {
        return builder.append("S");
      }
      return builder.append("V");
    } else if (cls.isArray()) {
      return toDescriptor(cls.getComponentType(), builder.append('['));
    }
    final String classname = cls.getName();
    builder.append('L');
    for (int i = 0, len = classname.length(); i < len; i++) {
      char c = classname.charAt(i);
      if(c == '.') {
        c = '/';
      }
      builder.append(c);
    }
    return builder.append(';');
  }

  /**
   * @param classname
   * @return
   */
  public static String toDescriptor(String classname) {
    return toDescriptor(classname, 0, classname.length());
  }

  /**
   * @param classname is a common binary classname like: java.lang.String or int[]
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
   * @param classnameA is either a binary or internal classname
   * @param classnameB is either a binary or internal classname
   * @return
   */
  public static boolean equalClassnames(String classnameA, String classnameB) {
    return equalClassnames(classnameA, 0, classnameB, 0, Math.min(classnameA.length(), classnameB.length()));
  }

  /**
   * @param classnameA is either a binary or internal classname
   * @param offA is the begin of the part with classname A
   * @param classnameB is either a binary or internal classname
   * @param offB is the begin of the part with classname B
   * @param len is the overall length to compare
   * @return
   */
  public static boolean equalClassnames(String classnameA, int offA, String classnameB, int offB, int len) {
    for(int i = 0; i < len; i++) {
      char a = classnameA.charAt(offA + i);
      char b = classnameB.charAt(offB + i);

      if(a != b) {
        if((a == '.' && b == '/') || (a == '/' && b == '.')) {
          continue;
        }
        return false;
      }
    }
    return true;
  }

  /**
   * @param classname
   * @return
   */
  public static boolean isAnonymous(String classname) {
    return simpleNameLength(classname) == 0;
  }

  /**
   * @param classname
   * @return
   */
  public static boolean hasSimpleName(String classname) {
    return simpleNameLength(classname) > 0;
  }

  /**
   * @param classname
   * @return
   */
  public static boolean hasSimpleBinaryName(String classname) {
    return classname.lastIndexOf('$') > -1;
  }

  /**
   * @param classname is the fully-qualified classname
   * @return <b>null</b> if top element or minimal simple-name of the referenced class
   */
  public static String toSimpleBinaryName(String classname) {
    int dollarIndex = classname.lastIndexOf('$');
    if (dollarIndex < 0) {
      return null;
    }
    return classname.substring(dollarIndex);
  }

  /**
   * @param classname
   * @return
   */
  public static boolean isArray(String classname) {
    return classname.startsWith(DESC_ARRAY_PREFIX_STR) ||
           classname.endsWith(ARRAY_DIMENSION);
  }

  /**
   * @param classname
   * @return
   */
  public static int getDimension(String classname) {
    int dims = 0;
    //Do we have a descriptor form?
    if(classname.startsWith(DESC_ARRAY_PREFIX_STR)) {
      while(classname.charAt(dims) == DESC_ARRAY_PREFIX) {
        dims++;
      }
      return dims;
    }
    //We have a binary classname
    int offset = classname.length() - ARRAY_DIMENSION.length();
    while (offset >= 0 && classname.startsWith(ARRAY_DIMENSION, offset)) {
      offset -= ARRAY_DIMENSION.length();
      dims++;
    }
    return dims;
  }

  /**
   * @param classname is the fully-qualified binary classname
   * @return <b>null</b> if top element or minimal simple-name of the referenced class
   */
  public static String toSimpleName(String classname) {
    int dims = getDimension(classname);
    if (dims > 0) {
      final String componentClassname =
        classname.substring(0, classname.length() - dims * ARRAY_DIMENSION.length());
      final StringBuilder builder = new StringBuilder(toSimpleName(componentClassname));

      for (int i = 0; i < dims; i++) {
        builder.append(ARRAY_DIMENSION);
      }
      return builder.toString();
    }

    String simpleName = toSimpleBinaryName(classname);
    if (simpleName == null) {
      // top level class
      simpleName = classname;
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
}
