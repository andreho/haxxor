package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxMember;

/**
 * <br/>Created by a.hofmann on 24.10.2017 at 16:31.
 */
@FunctionalInterface
public interface HxInitializer<M extends HxMember<M>> {

  /**
   * Merges the stepped initialization process into one initialization step
   * @param initializers to use for initialization of an element that was created via {@link HxElementFactory}
   * @return chained element-initializer
   */
  static <E extends HxMember<E>> HxInitializer<E> merge(final HxInitializer<E>... initializers) {
    if (initializers == null || initializers.length == 0) {
      throw new IllegalArgumentException("Given initializer's array can't be null or empty.");
    }
    for (int i = 0; i < initializers.length; i++) {
      HxInitializer<E> initializer = initializers[i];
      if (initializer == null) {
        throw new IllegalArgumentException("Initializer at index " + i + " is null.");
      }
    }

    final HxInitializer<E>[] array = initializers.clone();

    return element -> {
      for (HxInitializer<E> initializer : array) {
        initializer.initialize(element);
      }
    };
  }

  /**
   * Initializes, prepares or adapts intern properties of the given member element
   * @param member to modify according to user's needs
   */
  void initialize(M member);
}
