package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class IntegerArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<int[], int[]> {

  public IntegerArrayAnnotationAttribute(final String name, final int[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof int[]) {
      int[] array = (int[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<int[], int[]> clone() {
    return new IntegerArrayAnnotationAttribute(getName(), getValue());
  }
}
