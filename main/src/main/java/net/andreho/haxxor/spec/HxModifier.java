package net.andreho.haxxor.spec;

import java.util.Collection;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public interface HxModifier {
   /**
    * Transforms given modifiers to raw bit-set representation
    *
    * @param collection of modifiers to merge
    * @return corresponding modifier bit-set
    */
   static int toBits(Collection<? extends HxModifier> collection) {
      int bits = 0;

      for (HxModifier modifier : collection) {
         bits |= modifier.toBit();
      }

      return bits;
   }

   /**
    * Transforms given modifiers to raw bit-set representation
    *
    * @param modifiers to merge
    * @return corresponding modifier bit-set
    */
   static int toBits(HxModifier... modifiers) {
      int bits = 0;

      for (HxModifier modifier : modifiers) {
         bits |= modifier.toBit();
      }

      return bits;
   }

   /**
    * @return an integer with a set bit that corresponds to this modifier
    */
   int toBit();
}
