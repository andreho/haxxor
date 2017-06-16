package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxConstants;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

import java.lang.reflect.Array;

/**
 * <br/>Created by a.hofmann on 14.06.2017 at 05:28.
 */
public abstract class AbstractArrayAnnotationAttribute<V, T> extends AbstractAnnotationAttribute<V, T> {

  public AbstractArrayAnnotationAttribute(final String name, final V value) {
    super(name, value);
  }

  public boolean isEmpty() {
    return Array.getLength(getValue()) == 0;
  }

  @Override
  public String toString() {
    final Object value = getValue();
    final StringBuilder builder = new StringBuilder();

    if(value == null) {
      return builder.append("null").toString();
    }
    if(value == HxConstants.EmptyArray.INSTANCE) {
      return builder.append("[]").toString();
    }

    builder.append('[');
    final int len = Array.getLength(value);
    if(len > 0) {
      builder.append(Array.get(value, 0));
      for (int i = 1; i < len; i++) {
        builder.append(',').append(Array.get(value, i));
      }
    }
    return builder.append(']').toString();
  }
}
