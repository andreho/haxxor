package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxEnum;

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
  public void setValue(final HxEnum[] value) {
    super.setValue(value);
  }

  @Override
  public HxAnnotationAttribute<HxEnum[], E[]> clone() {
    return new EnumArrayAnnotationAttribute<>(getName(), getValue());
  }
}
