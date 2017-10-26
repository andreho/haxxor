package net.andreho.haxxor.api.impl.annotation;

import net.andreho.haxxor.api.HxAnnotationAttribute;

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
