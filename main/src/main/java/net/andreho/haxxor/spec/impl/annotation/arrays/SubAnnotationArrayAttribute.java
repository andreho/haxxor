package net.andreho.haxxor.spec.impl.annotation.arrays;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxAnnotationAttribute;
import net.andreho.haxxor.spec.impl.annotation.AbstractAnnotationAttribute;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class SubAnnotationArrayAttribute
    extends AbstractAnnotationAttribute<HxAnnotation[], Annotation[]> {

//  private volatile Annotation[] annotations;

  public SubAnnotationArrayAttribute(final String name, final HxAnnotation[] values) {
    super(name, values);
  }

  @Override
  public Annotation[] original(Class<?> type) {
    throw new UnsupportedOperationException();
//    if (this.annotations == null) {
//      final Annotation[] array = this.annotations = new Annotation[getValue().length];
//      for (int i = 0; i < array.length; i++) {
//        array[i] = getValue()[i].getView();
//      }
//    }
//    return this.annotations;
  }

  @Override
  public HxAnnotationAttribute<HxAnnotation[], Annotation[]> clone() {
    return new SubAnnotationArrayAttribute(getName(), getValue());
  }
}
