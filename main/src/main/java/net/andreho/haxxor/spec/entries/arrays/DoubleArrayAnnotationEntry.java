package net.andreho.haxxor.spec.entries.arrays;

import net.andreho.haxxor.spec.entries.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class DoubleArrayAnnotationEntry extends AbstractAnnotationEntry<double[], double[]> {
   public DoubleArrayAnnotationEntry(final String name, final double... values) {
      super(name, values);
   }
}
