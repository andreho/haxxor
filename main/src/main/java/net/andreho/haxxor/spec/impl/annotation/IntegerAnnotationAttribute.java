package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class IntegerAnnotationAttribute
    extends AbstractAnnotationAttribute<Integer, Integer> {

  public IntegerAnnotationAttribute(final String name, final int value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Integer, Integer> clone() {
    return new IntegerAnnotationAttribute(getName(), getValue());
  }
}
