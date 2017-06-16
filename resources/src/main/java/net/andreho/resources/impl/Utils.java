package net.andreho.resources.impl;

import net.andreho.resources.Resource;

import java.util.Map;

/**
 * <br/>Created by a.hofmann on 16.06.2017 at 03:17.
 */
public abstract class Utils {

  private Utils() {
  }

  /**
   * Merges multiple entries by name into the result map
   *
   * @param result
   * @param other
   */
  public static void mergeResults(final Map<String, Resource> result,
                                  final Map<String, Resource> other) {
    for (final Map.Entry<String, Resource> entry : other.entrySet()) {
      final String key = entry.getKey();
      final Resource value = entry.getValue();

      result.merge(key, value, (prev, next) -> {
        next.setNext(prev);
        return next;
      });
    }
  }
}
