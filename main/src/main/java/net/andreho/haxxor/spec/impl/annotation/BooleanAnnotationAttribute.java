package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class BooleanAnnotationAttribute
    extends AbstractAnnotationAttribute<Boolean, Boolean> {

  public BooleanAnnotationAttribute(final String name,
                                    final boolean value) {

    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Boolean, Boolean> clone() {
    return new BooleanAnnotationAttribute(getName(), getValue());
  }
}
