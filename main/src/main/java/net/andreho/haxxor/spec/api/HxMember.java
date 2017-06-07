package net.andreho.haxxor.spec.api;


/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMember<M extends HxMember<M>> {

  /**
   * Changes modifiers to the given set of modifiers
   * @param modifiers to apply
   * @return this
   */
  M setModifiers(HxModifier... modifiers);

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
   * @param modifiers
   * @return
   */
  default boolean hasModifiers(HxModifier... modifiers) {
    int mask = 0;
    for (HxModifier modifier : modifiers) {
      mask |= modifier.toBit();
    }
    return hasModifiers(mask);
  }
}
