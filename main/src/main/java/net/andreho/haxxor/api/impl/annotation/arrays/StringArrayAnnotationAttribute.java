package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class StringArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<String[], String[]> {

  public StringArrayAnnotationAttribute(final String name, final String[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof String[]) {
      String[] array = (String[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<String[], String[]> clone() {
    return new StringArrayAnnotationAttribute(getName(), getValue());
  }
}
