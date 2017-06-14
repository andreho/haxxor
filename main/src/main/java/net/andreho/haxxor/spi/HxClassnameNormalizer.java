package net.andreho.haxxor.spi;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:50.
 */
@FunctionalInterface
public interface HxClassnameNormalizer {
  /**
   * Transforms given typename if needed to a fully-qualified Java classname form
   * (like: int, byte[], java.lang.String etc.)
   *
   * @param typeName to transform (this may be any JBC type like an internal classname or a type descriptor)
   * @return corresponding typename in normalized form
   */
  String toNormalizedClassname(String typeName);

  /**
   * @param typeNames
   * @return
   */
  default String[] toNormalizedClassnames(String ... typeNames) {
    Objects.requireNonNull(typeNames,"Given name-array can't be null.");
    if(typeNames.length == 0) {
      return typeNames;
    }
    final String[] names = new String[typeNames.length];
    for (int i = 0; i < typeNames.length; i++) {
      names[i] = toNormalizedClassname(typeNames[i]);
    }
    return names;
  }
}
