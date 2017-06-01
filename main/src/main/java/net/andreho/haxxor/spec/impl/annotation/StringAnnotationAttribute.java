package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

/**
 * Created by a.hofmann on 25.05.2016.
 */
public class StringAnnotationAttribute
    extends AbstractAnnotationAttribute<String, String> {

  public StringAnnotationAttribute(final String name, final String value) {
    super(name, value);
  }

  @Override
  public HxAnnotationAttribute<String, String> clone() {
    return new StringAnnotationAttribute(getName(), getValue());
  }
}
