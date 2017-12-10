package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <br/>Created by a.hofmann on 22.06.2017 at 11:52.
 */
public class HxAnnotatedDelegate<A extends HxAnnotated<A>> implements HxAnnotated<A> {
  private volatile HxAnnotated annotated;

  private HxAnnotated<A> initAnnotated() {
    HxAnnotated annotated = this.annotated;
    if (annotated == null) {
      this.annotated = annotated = new HxAnnotatedImpl<>();
    }
    return annotated;
  }

  private A asAnnotated() {
    return (A) this;
  }

  public boolean hasAnnotations() {
    return this.annotated != null &&
           !this.annotated.getAnnotations()
                          .isEmpty();
  }

  @Override
  public List<HxAnnotation> getAnnotations() {
    HxAnnotated annotated = this.annotated;
    if(annotated == null) {
      return HxAnnotated.DEFAULT_ANNOTATION_COLLECTION;
    }
    return annotated.getAnnotations();
  }

  @Override
  public A setAnnotations(final Collection<HxAnnotation> annotations) {
    initAnnotated().setAnnotations(annotations);
    return asAnnotated();
  }

  @Override
  public A addAnnotation(final HxAnnotation annotation) {
    initAnnotated().addAnnotation(annotation);
    return asAnnotated();
  }

  @Override
  public A addRepeatableAnnotationIfNeeded(final HxAnnotation annotation,
                                                    final String repeatableAnnotationClassname) {
    initAnnotated().addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationClassname);
    return asAnnotated();
  }

  @Override
  public List<HxAnnotated> getSuperAnnotated() {
    return HxAnnotated.DEFAULT_SUPER_ANNOTATED_COLLECTION;
  }

  @Override
  public List<HxAnnotation> getAnnotationsByType(final String type) {
    if(!hasAnnotations()) {
      return Collections.emptyList();
    }
    return initAnnotated().getAnnotationsByType(type);
  }

  @Override
  public List<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate,
                                              final boolean recursive) {
    if(!hasAnnotations()) {
      return Collections.emptyList();
    }
    return initAnnotated().annotations(predicate, recursive);
  }
}
