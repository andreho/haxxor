package net.andreho.haxxor.spec.api;


/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMember<M extends HxMember<M>> {
  /**
   * Changes modifiers bit-set to the given set of modifiers
   * @param modifiers
   * @return this
   */
  M setModifiers(int modifiers);

  /**
   * @return applied modifiers (e.g.: public/private/volatile/abstract etc.)
   */
  int getModifiers();

  /**
   * Changes modifiers to the given set of modifiers
   * @param modifier to apply
   * @param rest is of the modifiers to apply
   * @return this
   */
  default M addModifiers(HxModifier modifier, HxModifier... rest) {
    return addModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }

  /**
   * Merges current modifiers bit-set with the given set of modifiers
   * @param modifiers
   * @return this
   */
  default M addModifiers(int modifiers) {
    return setModifiers(getModifiers() | modifiers);
  }

  /**
   * Changes modifiers to the given set of modifiers
   * @param modifier to apply
   * @param rest is of the modifiers to apply
   * @return this
   */
  default M setModifiers(HxModifier modifier, HxModifier... rest) {
    return setModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }

  /**
   * @param bitSet
   * @return
   */
  default boolean hasModifiers(int bitSet) {
    if(0 == bitSet) {
      throw new IllegalArgumentException("Invalid mask for modifiers: "+bitSet);
    }
    return (getModifiers() & bitSet) == bitSet;
  }

  /**
   * @param modifier
   * @return
   */
  default boolean hasModifiers(HxModifier modifier) {
    return hasModifiers(modifier.toBit());
  }

  /**
   * @param modifier
   * @param rest
   * @return
   */
  default boolean hasModifiers(HxModifier modifier, HxModifier... rest) {
    return hasModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }
}
