package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class CharacterArrayAnnotationAttribute
    extends AbstractAnnotationAttribute<char[], char[]> {

  public CharacterArrayAnnotationAttribute(final String name, final char[] values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<char[], char[]> clone() {
    return new CharacterArrayAnnotationAttribute(getName(), getValue());
  }
}
