package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class StringArrayAnnotationEntry extends AbstractAnnotationEntry<String[], String[]> {
   public StringArrayAnnotationEntry(final String name, final String... values) {
      super(name, values);
   }
}
