package net.andreho.haxxor.spec.api;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 11.06.2017 at 23:38.
 */
public interface HxGeneric<G extends HxGeneric<G>> {
  /**
   * @return <b>true</b> if this element has some generic information and <b>false</b> otherwise.
   */
  default boolean isGeneric() {
    return getGenericSignature().isPresent();
  }

  /**
   * @return raw generic signature of this element
   */
  default Optional<String> getGenericSignature() {
    return Optional.empty();
  }

  /**
   * @param genericSignature new raw generic signature
   * @return
   */
  default G setGenericSignature(String genericSignature) {
    return (G) this;
  }
}
