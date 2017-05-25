package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class BooleanArrayAnnotationEntry extends AbstractAnnotationEntry<boolean[], boolean[]> {
   public BooleanArrayAnnotationEntry(final String name, final boolean... values) {
      super(name, values);
   }
}
