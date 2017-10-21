package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxTypeInitializer {

  /**
   * Merges the stepped initialization process into one initialization step
   * @param initializers to use for initialization of a new type created by {@link HxElementFactory#createType(String)}
   * @return chained type-initializer
   */
  static HxTypeInitializer merge(final HxTypeInitializer... initializers) {
    if (initializers == null || initializers.length == 0) {
      throw new IllegalArgumentException("Given initializer's array can't be null or empty.");
    }
    for (int i = 0; i < initializers.length; i++) {
      HxTypeInitializer initializer = initializers[i];
      if (initializer == null) {
        throw new IllegalArgumentException("Type-Initializer at index " + i + " is null.");
      }
    }
    return type -> {
      for (HxTypeInitializer initializer : initializers) {
        initializer.initialize(type);
      }
    };
  }

  /**
   * Initializes, prepares or adapts intern properties of the given type
   * @param type to modify according to user's needs
   */
  void initialize(HxType type);
}
