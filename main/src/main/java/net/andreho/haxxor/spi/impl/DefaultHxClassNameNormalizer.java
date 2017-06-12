package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassNameNormalizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static net.andreho.haxxor.spec.api.HxConstants.ARRAY_DIMENSION;
import static net.andreho.haxxor.spec.api.HxConstants.DESC_ARRAY_PREFIX;
import static net.andreho.haxxor.spec.api.HxConstants.DESC_PREFIX;
import static net.andreho.haxxor.spec.api.HxConstants.DESC_SUFFIX;
import static net.andreho.haxxor.spec.api.HxConstants.INTERNAL_PACKAGE_SEPARATOR_CHAR;
import static net.andreho.haxxor.spec.api.HxConstants.JAVA_PACKAGE_SEPARATOR_CHAR;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:52.
 */
public class DefaultHxClassNameNormalizer
    implements HxClassNameNormalizer {

  private static final Map<String, String> PRIMITIVES;

  static {
    Map<String, String> map = new HashMap<>();
    map.put("V", "void");
    map.put("Z", "boolean");
    map.put("B", "byte");
    map.put("S", "short");
    map.put("C", "char");
    map.put("I", "int");
    map.put("F", "float");
    map.put("J", "long");
    map.put("D", "double");

    PRIMITIVES = Collections.unmodifiableMap(map);
  }

  @Override
  public String toNormalizedClassName(final String typeName) {
    boolean desc = false;
    int dim = 0;
    int off = 0;
    int len = typeName.length();

    while (typeName.charAt(off) == DESC_ARRAY_PREFIX) {
      dim++;
      off++;
    }

    //Do we have a descriptor form? [Ljava/lang/String;
    if (typeName.charAt(len - 1) == DESC_SUFFIX) {
      if (typeName.charAt(off) != DESC_PREFIX) {
        throw new IllegalArgumentException("Invalid type descriptor: " + typeName);
      }
      desc = true;
      off++; //skip leading 'L'
      len--; //skip trailing ';'
    }

    String type = typeName.substring(off, len);
    String primitive = null;

    //Do we had an internal array representation, like: [B, [[I etc.
    if (dim > 0 && type.length() == 1) {
      primitive = PRIMITIVES.get(type);
    }

    if (primitive != null) {
      type = primitive;
    } else if (type.indexOf(JAVA_PACKAGE_SEPARATOR_CHAR) == -1 && type.indexOf(INTERNAL_PACKAGE_SEPARATOR_CHAR) > -1) {
      type = type.replace(INTERNAL_PACKAGE_SEPARATOR_CHAR, JAVA_PACKAGE_SEPARATOR_CHAR);
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
