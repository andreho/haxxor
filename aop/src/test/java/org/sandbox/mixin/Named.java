package org.sandbox.mixin;

/**
 * <br/>Created by a.hofmann on 30.09.2017 at 03:27.
 */
public interface Named {
  default String getName() {
    throw new UnsupportedOperationException();
  }
  default void setName(String name) {
    throw new UnsupportedOperationException();
  }
}
