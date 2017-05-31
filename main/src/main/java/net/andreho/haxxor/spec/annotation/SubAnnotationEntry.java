package net.andreho.haxxor.spec.annotation;

import net.andreho.haxxor.spec.api.HxAnnotation;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by andreho on 4/4/16 at 11:22 PM.<br/>
 */
public class SubAnnotationEntry
    extends AbstractAnnotationEntry<HxAnnotation, Annotation> {

  private Annotation annotation;

  public SubAnnotationEntry(final String name, final HxAnnotation value) {
    super(name, value);
  }

  @Override
  public Annotation original(Class<?> type) {
    if (this.annotation == null) {
      this.annotation = get().getView();
    }
    return annotation;
  }
}
