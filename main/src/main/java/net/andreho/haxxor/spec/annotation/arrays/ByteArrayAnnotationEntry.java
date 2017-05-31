package net.andreho.haxxor.spec.annotation.arrays;

import net.andreho.haxxor.spec.annotation.AbstractAnnotationEntry;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ByteArrayAnnotationEntry
    extends AbstractAnnotationEntry<byte[], byte[]> {

  public ByteArrayAnnotationEntry(final String name, final byte... values) {
    super(name, values);
  }
}
