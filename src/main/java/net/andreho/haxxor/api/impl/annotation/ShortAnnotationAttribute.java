package net.andreho.haxxor.api.impl.annotation;

import net.andreho.haxxor.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class ShortAnnotationAttribute
    extends AbstractAnnotationAttribute<Short, Short> {

  public ShortAnnotationAttribute(final String name, final short value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Short, Short> clone() {
    return new ShortAnnotationAttribute(getName(), getValue());
  }
}
