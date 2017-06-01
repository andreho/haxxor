package net.andreho.haxxor.spec.impl.annotation;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxAnnotationAttribute;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class SubAnnotationAttribute
    extends AbstractAnnotationAttribute<HxAnnotation, Annotation> {

//  private Annotation annotation;

  public SubAnnotationAttribute(final String name, final HxAnnotation value) {
    super(name, value);
  }

  @Override
  public Annotation original(Class<?> type) {
    throw new UnsupportedOperationException();
//    if (this.annotation == null) {
//      this.annotation = getValue().getView();
//    }
//    return annotation;
  }

  @Override
  public HxAnnotationAttribute<HxAnnotation, Annotation> clone() {
    return new SubAnnotationAttribute(getName(), getValue());
  }
}
