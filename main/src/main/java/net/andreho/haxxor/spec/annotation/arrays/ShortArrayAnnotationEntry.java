package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ShortArrayAnnotationEntry
    extends AbstractAnnotationEntry<short[], short[]> {

  public ShortArrayAnnotationEntry(final String name, final short... values) {
    super(name, values);
  }
}
