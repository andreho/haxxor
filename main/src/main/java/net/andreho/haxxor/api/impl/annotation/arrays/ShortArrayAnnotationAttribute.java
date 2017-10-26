package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ShortArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<short[], short[]> {

  public ShortArrayAnnotationAttribute(final String name, final short[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof short[]) {
      short[] array = (short[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<short[], short[]> clone() {
    return new ShortArrayAnnotationAttribute(getName(), getValue());
  }
}
