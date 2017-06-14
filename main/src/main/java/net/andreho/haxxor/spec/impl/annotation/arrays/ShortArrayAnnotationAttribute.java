package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ShortArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<short[], short[]> {

  public ShortArrayAnnotationAttribute(final String name, final short[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<short[], short[]> clone() {
    return new ShortArrayAnnotationAttribute(getName(), getValue());
  }
}
