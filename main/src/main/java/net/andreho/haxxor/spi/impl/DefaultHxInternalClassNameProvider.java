package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxInternalClassNameProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:52.
 */
public class DefaultHxInternalClassNameProvider
    implements HxInternalClassNameProvider {

  private static final int LONGEST_PRIMITIVE_NAME = "boolean".length();
  private static final char ARRAY_PREFIX = '[';
  private static final char ARRAY_SUFFIX = ']';
  private static final char DESC_PREFIX = 'L';
  private static final char DESC_SUFFIX = ';';
  private static final char JAVA_PACKAGE_DELIMITER = '.';
  private static final char INTERNAL_PACKAGE_DELIMITER = '/';
  private static final Map<String, String> PRIMITIVES;

  static {
    Map<String, String> map = new HashMap<>();
    map.put("void", "V");
    map.put("boolean", "Z");
    map.put("byte", "B");
    map.put("short", "S");
    map.put("char", "C");
    map.put("int", "I");
    map.put("float", "F");
    map.put("long", "J");
    map.put("double", "D");
    PRIMITIVES = Collections.unmodifiableMap(map);
  }

  @Override
  public String toInternalClassName(final String typeName) {
    int off = 0;
    int len = typeName.length();

    if (typeName.charAt(off) == ARRAY_PREFIX || typeName.charAt(len - 1) == ARRAY_SUFFIX) {
      throw new IllegalArgumentException("Classname of an array can't be represented as internal classname: "+typeName);
    }

    //Do we have a descriptor form? [Ljava/lang/String;
    if (typeName.charAt(off) == DESC_PREFIX &&
        typeName.charAt(len - 1) == DESC_SUFFIX) {
      off++; //skip leading 'L'
      len--; //skip trailing ';'
    }

    String type = typeName.substring(off, len);
    String primitive = null;

    if(type.length() <= LONGEST_PRIMITIVE_NAME) {
      primitive = PRIMITIVES.get(type);
    }

    if (primitive != null) {
      type = primitive;
    } else {
      type = type.replace(JAVA_PACKAGE_DELIMITER, INTERNAL_PACKAGE_DELIMITER);
    }

    return type;
  }
}
