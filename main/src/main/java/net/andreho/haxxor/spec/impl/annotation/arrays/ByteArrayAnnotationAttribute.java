package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ByteArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<byte[], byte[]> {

  public ByteArrayAnnotationAttribute(final String name, final byte[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<byte[], byte[]> clone() {
    return new ByteArrayAnnotationAttribute(getName(), getValue());
  }
}
