package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class LongArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<long[], long[]> {

  public LongArrayAnnotationAttribute(final String name, final long[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<long[], long[]> clone() {
    return new LongArrayAnnotationAttribute(getName(), getValue());
  }
}
