package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class FloatArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<float[], float[]> {

  public FloatArrayAnnotationAttribute(final String name, final float[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<float[], float[]> clone() {
    return new FloatArrayAnnotationAttribute(getName(), getValue());
  }
}
