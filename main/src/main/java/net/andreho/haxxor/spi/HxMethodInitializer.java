package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxMethodInitializer {

  /**
   * Merges the stepped initialization process into one initialization step
   * @param initializers to use for initialization of a new type created by {@link HxElementFactory#createMethod(String, String, String...)}
   * @return chained method-initializer
   */
  static HxMethodInitializer merge(final HxMethodInitializer... initializers) {
    if (initializers == null || initializers.length == 0) {
      throw new IllegalArgumentException("Given initializer's array can't be null or empty.");
    }
    for (int i = 0; i < initializers.length; i++) {
      HxMethodInitializer initializer = initializers[i];
      if (initializer == null) {
        throw new IllegalArgumentException("Method-Initializer at index " + i + " is null.");
      }
    }
    return field -> {
      for (HxMethodInitializer initializer : initializers) {
        initializer.initialize(field);
      }
    };
  }

  /**
   * Initializes, prepares or adapts intern properties of the given method
   * @param method to modify according to user's needs
   */
  void initialize(HxMethod method);
}
