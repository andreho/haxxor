package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassNameSupplier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:52.
 */
public class DefaultHxClassNameSupplier
    implements HxClassNameSupplier {

  private static final Map<String, String> PRIMITIVES;
  private static final String ARRAY_DIMENSION = "[]";
  private static final char ARRAY_PREFIX = '[';
  private static final char DESC_PREFIX = 'L';
  private static final char DESC_SUFFIX = ';';
  private static final char JAVA_PACKAGE_DELIMITER = '.';
  private static final char INTERNAL_PACKAGE_DELIMITER = '/';

  static {
    Map<String, String> map = new HashMap<>();
    map.put("V","void");
    map.put("Z","boolean");
    map.put("B","byte");
    map.put("S","short");
    map.put("C","char");
    map.put("I","int");
    map.put("F","float");
    map.put("J","long");
    map.put("D","double");

    PRIMITIVES = Collections.unmodifiableMap(map);
  }

  @Override
  public String toJavaClassName(final String typeName) {
    boolean desc = false;
    int dim = 0;
    int off = 0;
    int len = typeName.length();

    while (typeName.charAt(off) == ARRAY_PREFIX) {
      dim++;
      off++;
    }

    //Do we have a descriptor form? [Ljava/lang/String;
    if (typeName.charAt(len - 1) == DESC_SUFFIX) {
      if(typeName.charAt(off) != DESC_PREFIX) {
        throw new IllegalArgumentException("Invalid type descriptor: "+typeName);
      }
      desc = true;
      off++; //skip leading 'L'
      len--; //skip trailing ';'
    }

    String type = typeName.substring(off, len);
    String primitive = null;

    //Do we had an internal array representation, like: [B, [[I etc.
    if(dim > 0 && type.length() == 1) {
      primitive = PRIMITIVES.get(type);
    }

    if (primitive != null) {
      type = primitive;
    } else if (type.indexOf(JAVA_PACKAGE_DELIMITER) == -1 &&
               type.indexOf(INTERNAL_PACKAGE_DELIMITER) > -1) {
      type = type.replace(INTERNAL_PACKAGE_DELIMITER, JAVA_PACKAGE_DELIMITER);
    }

    if (desc || dim > 0) {
      final StringBuilder builder = new StringBuilder(type.length() + dim * 2);
      builder.append(type);
      while (dim-- > 0) {
        builder.append(ARRAY_DIMENSION);
      }
      return builder.toString();
    }
    return type;
  }
}
