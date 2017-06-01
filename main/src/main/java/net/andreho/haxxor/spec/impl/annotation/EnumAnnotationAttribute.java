package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.api.HxEnum;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class EnumAnnotationAttribute
    extends AbstractAnnotationAttribute<HxEnum, Enum> {

  public EnumAnnotationAttribute(final String name, final HxEnum value) {
    super(name, value);
  }

  @Override
  public Enum original(Class<?> type) {
    return getValue().loadEnum();
  }

  @Override
  public HxAnnotationAttribute<HxEnum, Enum> clone() {
    return new EnumAnnotationAttribute(getName(), getValue());
  }
}
