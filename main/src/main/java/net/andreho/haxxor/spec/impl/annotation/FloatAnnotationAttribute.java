package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class FloatAnnotationAttribute
    extends AbstractAnnotationAttribute<Float, Float> {

  public FloatAnnotationAttribute(final String name, final float value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Float, Float> clone() {
    return new FloatAnnotationAttribute(getName(), getValue());
  }
}
