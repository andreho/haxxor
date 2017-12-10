package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 07:42.
 */
public interface HxInitializable<O> {
  /**
   * Initializes given element's part
   *
   * @param part to initialize
   * @return owner instance
   * @see HxInitializablePart#ANNOTATIONS
   * @see HxInitializablePart#FIELDS
   * @see HxInitializablePart#METHODS
   * @see HxInitializablePart#INTERFACES
   * @see HxInitializablePart#INNER_TYPES
   */
  default O initialize(HxInitializablePart part) {
    return (O) this;
  }

  /**
   * Initializes given parts of this element
   *
   * @param parts to initialize
   * @return owner instance
   * @see HxInitializablePart#ANNOTATIONS
   * @see HxInitializablePart#FIELDS
   * @see HxInitializablePart#METHODS
   * @see HxInitializablePart#INTERFACES
   * @see HxInitializablePart#INNER_TYPES
   */
  default O initialize(HxInitializablePart... parts) {
    for(HxInitializablePart part : parts) {
      initialize(part);
    }
    return (O) this;
  }
}
