package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class BooleanArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<boolean[], boolean[]> {

  public BooleanArrayAnnotationAttribute(final String name, final boolean[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<boolean[], boolean[]> clone() {
    return new BooleanArrayAnnotationAttribute(getName(), getValue());
  }
}
