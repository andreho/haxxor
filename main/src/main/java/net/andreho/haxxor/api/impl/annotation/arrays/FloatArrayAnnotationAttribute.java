package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class FloatArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<float[], float[]> {

  public FloatArrayAnnotationAttribute(final String name, final float[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof float[]) {
      float[] array = (float[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<float[], float[]> clone() {
    return new FloatArrayAnnotationAttribute(getName(), getValue());
  }
}
