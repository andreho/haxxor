package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class BooleanArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<boolean[], boolean[]> {

  public BooleanArrayAnnotationAttribute(final String name, final boolean[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof boolean[]) {
      boolean[] array = (boolean[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<boolean[], boolean[]> clone() {
    return new BooleanArrayAnnotationAttribute(getName(), getValue());
  }
}
