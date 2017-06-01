package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

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
