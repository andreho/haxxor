package net.andreho.haxxor.api;

/**
 * <br/>Created by a.hofmann on 30.11.2017 at 07:42.
 */
public interface HxInitializable<P extends HxInitializablePart, O extends HxInitializable<P, O>> {
  /**
   * Initializes given element's part
   *
   * @param part to initialize
   * @return owner instance
   */
  O initialize(P part);

  /**
   * Initializes given parts of this element
   *
   * @param parts to initialize
   * @return owner instance
   */
  default O initialize(P... parts) {
    for(P part : parts) {
      initialize(part);
    }
    return (O) this;
  }
}
