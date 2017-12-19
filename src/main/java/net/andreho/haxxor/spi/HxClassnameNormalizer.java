package net.andreho.haxxor.spi;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 12:50.
 */
@FunctionalInterface
public interface HxClassnameNormalizer {
  /**
   * Transforms given typename if needed to a fully-qualified binary Java classname form
   * (like: int, byte[], java.lang.Object, java.lang.String[], etc.)
   *
   * @param typeName to transform (this may be any JBC type like an internal classname or a type descriptor)
   * @return corresponding typename in normalized form
   */
  String toNormalizedClassname(String typeName);

  /**
   * @param cls
   * @return
   */
  default String toNormalizedClassname(Class<?> cls) {
    return toNormalizedClassname(cls.getName());
  }

  /**
   * @param typeNames to normalize
   * @return a copy with normalized type-names
   */
  default String[] toNormalizedClassnames(final String ... typeNames) {
    Objects.requireNonNull(typeNames,"Given name-array can't be null.");
    if(typeNames.length == 0) {
      return typeNames;
    }
    final String[] names = typeNames.clone();
    for (int i = 0; i < names.length; i++) {
      names[i] = toNormalizedClassname(names[i]);
    }
    return names;
  }

  /**
   * @param classes to normalize
   * @return a copy with normalized type-names
   */
  default String[] toNormalizedClassnames(final Class<?> ... classes) {
    Objects.requireNonNull(classes,"Given class-array can't be null.");
    if(classes.length == 0) {
      return new String[0];
    }
    final String[] typeNames = new String[classes.length];
    for (int i = 0; i < typeNames.length; i++) {
      typeNames[i] = toNormalizedClassname(classes[i]);
    }
    return typeNames;
  }
}
