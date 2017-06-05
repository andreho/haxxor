package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class StringArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<String[], String[]> {

  public StringArrayAnnotationAttribute(final String name, final String[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<String[], String[]> clone() {
    return new StringArrayAnnotationAttribute(getName(), getValue());
  }
}