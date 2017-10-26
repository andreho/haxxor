package net.andreho.haxxor.api;


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
   * Removes the given modifiers from this modifiers' bit-set
   * @param modifiers to remove
   * @return this
   */
  default M removeModifiers(int modifiers) {
    return setModifiers(getModifiers() & (~modifiers));
  }

  /**
   * Removes the given modifier from this modifiers' bit-set
   * @param modifier to remove
   * @return this
   */
  default M removeModifier(HxModifier modifier) {
    return removeModifiers(modifier.toBit());
  }

  /**
   * Removes the given modifiers from this modifiers' bit-set
   * @param modifier to remove
   * @param rest of modifiers to remove
   * @return this
   */
  default M removeModifiers(HxModifier modifier, HxModifier... rest) {
    return removeModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }

  /**
   * Adds given modifier to the current bit-set with modifiers
   * @param modifier to apply
   * @return this
   */
  default M addModifier(HxModifier modifier) {
    return setModifiers(getModifiers() | modifier.toBit());
  }

  /**
   * Merges current modifiers bit-set with the given set of modifiers
   * @param modifier to apply
   * @param rest of modifiers to apply
   * @return this
   */
  default M addModifiers(HxModifier modifier, HxModifier... rest) {
    return addModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }

  /**
   * Merges current modifiers' bit-set with the given set of modifiers
   * @param modifiers
   * @return this
   */
  default M addModifiers(int modifiers) {
    return setModifiers(getModifiers() | modifiers);
  }

  /**
   * Changes modifiers to the given set of modifiers
   * @param modifier to apply
   * @param rest of the modifiers to apply
   * @return this
   */
  default M setModifiers(HxModifier modifier, HxModifier... rest) {
    return setModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }

  /**
   * @param bitSet to test against
   * @return <b>true</b> if this member has all requested modifiers
   */
  default boolean hasModifiers(int bitSet) {
    if(0 == bitSet) {
      throw new IllegalArgumentException("Invalid mask for modifiers: "+bitSet);
    }
    return (getModifiers() & bitSet) == bitSet;
  }

  /**
   * @param modifier to search for
   * @return <b>true</b> if this member has the requested modifier
   */
  default boolean hasModifiers(HxModifier modifier) {
    return hasModifiers(modifier.toBit());
  }

  /**
   * @param modifier to search for
   * @param rest of other modifiers to look for
   * @return <b>true</b> if this member has all requested modifiers
   */
  default boolean hasModifiers(HxModifier modifier, HxModifier... rest) {
    return hasModifiers(modifier.toBit() | HxModifier.toBits(rest));
  }
}
