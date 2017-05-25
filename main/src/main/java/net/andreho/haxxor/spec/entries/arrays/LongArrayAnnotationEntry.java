package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class LongArrayAnnotationEntry extends AbstractAnnotationEntry<long[], long[]> {
   public LongArrayAnnotationEntry(final String name, final long... values) {
      super(name, values);
   }
}
