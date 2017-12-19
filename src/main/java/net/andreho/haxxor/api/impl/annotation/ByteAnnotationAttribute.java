package net.andreho.haxxor.api.impl.annotation;

import net.andreho.haxxor.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ByteAnnotationAttribute
    extends AbstractAnnotationAttribute<Byte, Byte> {

  public ByteAnnotationAttribute(final String name, final byte value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Byte, Byte> clone() {
    return new ByteAnnotationAttribute(getName(), getValue());
  }
}
