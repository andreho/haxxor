package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxConstants;

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
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o == HxConstants.EMPTY_ARRAY) {
      return true;
    }
    if(o != null && o.getClass().isArray()) {
      return 0 == Array.getLength(o);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<Object, Object> clone() {
    return new EmptyArrayAnnotationAttribute(getName(), getValue());
  }

  @Override
  public Object original(final Class<?> type) {
    if(type.isArray()) {
      return original(type.getComponentType());
    }
    return Array.newInstance(type, 0);
  }
}
