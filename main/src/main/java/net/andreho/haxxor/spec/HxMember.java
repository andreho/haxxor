package net.andreho.haxxor.spec;


/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxMember<M extends HxMember<M>> {
   /**
    * @param modifiers
    * @return
    */
   M setModifiers(HxModifier... modifiers);

   /**
    * @param modifiers
    * @return
    */
   M setModifiers(int modifiers);

   /**
    * @return
    */
   int getModifiers();

   /**
    * @param bitSet
    * @return
    */
   default boolean hasModifiers(int bitSet) {
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
