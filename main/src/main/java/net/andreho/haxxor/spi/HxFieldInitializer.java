package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 20:16.
 */
@FunctionalInterface
public interface HxFieldInitializer {

  /**
   * Merges the stepped initialization process into one initialization step
   * @param initializers to use for initialization of a new type created by {@link HxElementFactory#createField(HxType, String)}
   * @return chained field-initializer
   */
  static HxFieldInitializer merge(final HxFieldInitializer... initializers) {
    if (initializers == null || initializers.length == 0) {
      throw new IllegalArgumentException("Given initializer's array can't be null or empty.");
    }
    for (int i = 0; i < initializers.length; i++) {
      HxFieldInitializer initializer = initializers[i];
      if (initializer == null) {
        throw new IllegalArgumentException("Field-Initializer at index " + i + " is null.");
      }
    }
    return field -> {
      for (HxFieldInitializer initializer : initializers) {
        initializer.initialize(field);
      }
    };
  }

  /**
   * Initializes, prepares or adapts intern properties of the given field
   * @param field to modify according to user's needs
   */
  void initialize(HxField field);
}
