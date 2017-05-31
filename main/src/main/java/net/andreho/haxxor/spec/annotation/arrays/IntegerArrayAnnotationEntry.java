package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class IntegerArrayAnnotationEntry
    extends AbstractAnnotationEntry<int[], int[]> {

  public IntegerArrayAnnotationEntry(final String name, final int... values) {
    super(name, values);
  }
}
