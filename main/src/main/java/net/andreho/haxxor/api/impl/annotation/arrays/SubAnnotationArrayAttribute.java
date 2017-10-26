package net.andreho.haxxor.api.impl.annotation.arrays;

import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxAnnotationAttribute;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class SubAnnotationArrayAttribute
    extends AbstractArrayAnnotationAttribute<HxAnnotation[], Annotation[]> {
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
  public boolean hasValue(final Object o) {
    if(o instanceof HxAnnotation[]) {
      HxAnnotation[] array = (HxAnnotation[]) o;
      return Arrays.equals(getValue(), array);
    }
    return false;
  }

  @Override
  public HxAnnotationAttribute<HxAnnotation[], Annotation[]> clone() {
    return new SubAnnotationArrayAttribute(getName(), getValue());
  }
}
