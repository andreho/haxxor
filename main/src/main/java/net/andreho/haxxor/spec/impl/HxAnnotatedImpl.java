package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMember;
import net.andreho.haxxor.spec.api.HxOwned;
import net.andreho.haxxor.spec.api.HxType;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotatedImpl<A extends HxAnnotated<A> & HxMember<A> & HxOwned<A>>
    extends HxMemberImpl<A>
    implements HxAnnotated<A> {

  private static final Class<Repeatable> REPEATABLE_ANNOTATION_CLASS = Repeatable.class;
  protected Collection<HxAnnotation> annotations = DEFAULT_ANNOTATION_COLLECTION;

  public HxAnnotatedImpl() {
    super();
  }

  protected void cloneAnnotationsTo(HxAnnotatedImpl<A> other) {
    if(!getAnnotations().isEmpty()) {
      for(HxAnnotation annotation : other.getAnnotations()) {
        other.addAnnotation(annotation.clone());
      }
    } else {
      other.annotations = DEFAULT_ANNOTATION_COLLECTION;
    }
  }

  @Override
  public A setAnnotations(Collection<HxAnnotation> annotations) {
    if (this.annotations == DEFAULT_ANNOTATION_COLLECTION) {
      this.annotations = new LinkedHashSet<>();
    }

    this.annotations.clear();
    this.annotations.addAll(annotations);

    return (A) this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return DEFAULT_SUPER_ANNOTATED_COLLECTION;
  }

  @Override
  public Collection<HxAnnotation> getAnnotations() {
    return this.annotations;
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, boolean recursive) {
    final List<HxAnnotation> annotationList = new ArrayList<>(this.annotations.size());

    for (HxAnnotation annotation : getAnnotations()) {
      if (predicate.test(annotation)) {
        annotationList.add(annotation);
      }
    }

    if (recursive) {
      Collection<HxAnnotated> superAnnotated = getSuperAnnotated();

      for (HxAnnotated annotated : superAnnotated) {
        Collection<HxAnnotation> annotations = annotated.annotations(predicate, recursive);
        annotationList.addAll(annotations);
      }
    }

    return annotationList;
  }

  @Override
  public Collection<HxAnnotation> getAnnotationsByType(String type) {
    HxType repeatableAnnotationType = null;
    HxType repeatableType = null;

    for (HxAnnotation annotation : getAnnotations()) {
      repeatableAnnotationType = repeatableAnnotationType != null ?
                                 repeatableAnnotationType :
                                 annotation.getHaxxor()
                                           .resolve(type);

      final Optional<HxAnnotation> optional = repeatableAnnotationType.getAnnotation(REPEATABLE_ANNOTATION_CLASS);

      if (optional.isPresent()) {
        repeatableType = optional.get().attribute("value");
        break;
      }
    }

    if (repeatableType != null) {
      final Optional<HxAnnotation> optional = getAnnotation(repeatableType);

      if (optional.isPresent()) {
        HxAnnotation[] annotations = optional.get().attribute("value");
        return new ArrayList<>(Arrays.asList(annotations));
      }
    } else {
      final Optional<HxAnnotation> optional = getAnnotation(type);

      if (optional.isPresent()) {
        return new ArrayList<>(Arrays.asList(optional.get()));
      }
    }

    return Collections.emptySet();
  }
}
