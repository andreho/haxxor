package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class CharacterArrayAnnotationEntry extends AbstractAnnotationEntry<char[], char[]> {
   public CharacterArrayAnnotationEntry(final String name, final char... values) {
      super(name, values);
   }
}
