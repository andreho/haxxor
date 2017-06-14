package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

import java.lang.reflect.Array;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class EmptyArrayAnnotationAttribute
    extends AbstractArrayAnnotationAttribute<Object, Object> {

  public EmptyArrayAnnotationAttribute(final String name, final Object values) {
    super(name, values);
  }

  @Override
  public HxAnnotationAttribute<Object, Object> clone() {
    return new EmptyArrayAnnotationAttribute(getName(), getValue());
  }

  @Override
  public Object original(final Class<?> type) {
    return Array.newInstance(type, 0);
  }
}
