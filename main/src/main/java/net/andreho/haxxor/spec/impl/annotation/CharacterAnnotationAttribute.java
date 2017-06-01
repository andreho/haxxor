package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class CharacterAnnotationAttribute
    extends AbstractAnnotationAttribute<Character, Character> {

  public CharacterAnnotationAttribute(final String name, final char value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Character, Character> clone() {
    return new CharacterAnnotationAttribute(getName(), getValue());
  }
}
