package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class DoubleArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<double[], double[]> {

  public DoubleArrayAnnotationAttribute(final String name, final double[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof double[]) {
      double[] array = (double[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<double[], double[]> clone() {
    return new DoubleArrayAnnotationAttribute(getName(), getValue());
  }
}
