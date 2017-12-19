package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotationAttribute;
import net.andreho.haxxor.api.HxEnum;

import java.util.Arrays;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class EnumArrayAnnotationAttribute<E extends Enum<E>>
    extends AbstractArrayAnnotationAttribute<HxEnum[], E[]> {

  public EnumArrayAnnotationAttribute(final String name, final HxEnum[] values) {
    super(name, values);
  }

  @Override
  public E[] original(Class<?> type) {
    return HxEnum.toEnumArray((Class<E>) type, getValue());
  }

  @Override
  public boolean hasValue(final Object o) {
    if(o instanceof HxEnum[]) {
      HxEnum[] array = (HxEnum[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<HxEnum[], E[]> clone() {
    return new EnumArrayAnnotationAttribute<>(getName(), getValue());
  }
}
