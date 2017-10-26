package net.andreho.haxxor.api.impl.annotation;

import net.andreho.haxxor.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class DoubleAnnotationAttribute
    extends AbstractAnnotationAttribute<Double, Double> {

  public DoubleAnnotationAttribute(final String name, final double value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Double, Double> clone() {
    return new DoubleAnnotationAttribute(getName(), getValue());
  }
}
