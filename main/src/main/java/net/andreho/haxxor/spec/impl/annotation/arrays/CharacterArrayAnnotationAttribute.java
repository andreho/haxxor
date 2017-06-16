package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class CharacterArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<char[], char[]> {

  public CharacterArrayAnnotationAttribute(final String name, final char[] values) {
    super(name, values);
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof char[]) {
      char[] array = (char[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<char[], char[]> clone() {
    return new CharacterArrayAnnotationAttribute(getName(), getValue());
  }
}
