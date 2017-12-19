package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class LongArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<long[], long[]> {

  public LongArrayAnnotationAttribute(final String name, final long[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof long[]) {
      long[] array = (long[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<long[], long[]> clone() {
    return new LongArrayAnnotationAttribute(getName(), getValue());
  }
}
