package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class IntegerArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<int[], int[]> {

  public IntegerArrayAnnotationAttribute(final String name, final int[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<int[], int[]> clone() {
    return new IntegerArrayAnnotationAttribute(getName(), getValue());
  }
}
