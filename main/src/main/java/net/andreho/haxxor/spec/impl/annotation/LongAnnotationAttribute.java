package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class LongAnnotationAttribute
    extends AbstractAnnotationAttribute<Long, Long> {

  public LongAnnotationAttribute(final String name, final long value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<Long, Long> clone() {
    return new LongAnnotationAttribute(getName(), getValue());
  }
}
