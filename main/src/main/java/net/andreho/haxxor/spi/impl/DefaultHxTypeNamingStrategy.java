package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxTypeNamingStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:52.
 */
public class DefaultHxTypeNamingStrategy
    implements HxTypeNamingStrategy {

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
  public String toTypeName(final String typeName) {
    boolean desc = false;
    int dim = 0;
    int off = 0;
    int len = typeName.length();

    if (typeName.charAt(off) == '[') {
      do {
        dim++;
      }
      while (typeName.charAt(++off) == '[');
    } else if (typeName.charAt(len - 1) == ']') {
      do {
        dim++;
        len -= 2;
      }
      while (typeName.charAt(len - 1) == ']');
    }

    //Do we have a descriptor form? [Ljava/lang/String;
    if (typeName.charAt(off) == 'L' &&
        typeName.charAt(len - 1) == ';') {
      desc = true;
      off++; //skip leading 'L'
      len--; //skip trailing ';'
    }

    String type = typeName.substring(off, len);
    String primitive = PRIMITIVES.get(type);

    if (primitive != null) {
      type = primitive;
    } else if (type.indexOf('/') == -1 &&
               type.indexOf('.') > -1) {
      type = type.replace('.', '/');
    }

    if (dim > 0 && !desc) {
      final StringBuilder builder = new StringBuilder(type.length() + dim);
      while (dim-- > 0) {
        builder.append('[');
      }
      return builder.append(type)
                    .toString();
    }
    return type;
  }
}
