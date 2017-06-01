package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class DoubleArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<double[], double[]> {

  public DoubleArrayAnnotationAttribute(final String name, final double[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<double[], double[]> clone() {
    return new DoubleArrayAnnotationAttribute(getName(), getValue());
  }
}
