package net.andreho.haxxor.spec.impl;

import net.andreho.haxxor.Utils;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotatedImpl<A extends HxAnnotated<A> & HxMember<A> & HxOwned<A>>
    extends HxMemberImpl<A>
    implements HxAnnotated<A> {
  private static final Class<Repeatable> REPEATABLE_ANNOTATION_CLASS = Repeatable.class;
  protected Map<String, HxAnnotation> annotations = DEFAULT_ANNOTATION_MAP;

  public HxAnnotatedImpl() {
    super();
  }

  protected void cloneAnnotationsTo(HxAnnotatedImpl<A> other) {
    if(!getAnnotations().isEmpty()) {
      for(HxAnnotation annotation : other.getAnnotations().values()) {
        other.addAnnotation(annotation.clone());
      }
    } else {
      other.annotations = DEFAULT_ANNOTATION_MAP;
    }
  }

  @Override
  public A setAnnotations(final Map<String, HxAnnotation> annotations) {
    return setAnnotations(annotations.values());
  }

  @Override
  public A setAnnotations(Collection<HxAnnotation> annotations) {
    if(Utils.isUninitialized(annotations)) {
      this.annotations = DEFAULT_ANNOTATION_MAP;
      return (A) this;
    }

    Map<String, HxAnnotation> annotationsMap = this.annotations;

    if (annotationsMap == DEFAULT_ANNOTATION_MAP) {
      this.annotations = annotationsMap = new LinkedHashMap<>();
    }

    annotationsMap.clear();

    for(HxAnnotation annotation : annotations) {
      annotationsMap.putIfAbsent(annotation.getType().getName(), annotation);
    }

    return (A) this;
  }

  @Override
  public Collection<HxAnnotated> getSuperAnnotated() {
    return DEFAULT_SUPER_ANNOTATED_COLLECTION;
  }

  @Override
  public Map<String, HxAnnotation> getAnnotations() {
    return this.annotations;
  }

  @Override
  public Optional<HxAnnotation> getAnnotation(final String type) {
    final HxAnnotation annotation = this.annotations.get(type);
    if(annotation != null) {
      return Optional.of(annotation);
    }
    return Optional.empty();
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate, boolean recursive) {
    final List<HxAnnotation> annotationList = new ArrayList<>(this.annotations.size());

    for (HxAnnotation annotation : getAnnotations().values()) {
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

    for (HxAnnotation annotation : getAnnotations().values()) {
      repeatableAnnotationType = repeatableAnnotationType != null ?
                                 repeatableAnnotationType :
                                 annotation.getHaxxor()
                                           .resolve(type);

      final Optional<HxAnnotation> optional = repeatableAnnotationType.getAnnotation(REPEATABLE_ANNOTATION_CLASS);

      if (optional.isPresent()) {
        repeatableType = optional.get().getAttribute("value");
        break;
      }
    }

    if (repeatableType != null) {
      final Optional<HxAnnotation> optional = getAnnotation(repeatableType);

      if (optional.isPresent()) {
        HxAnnotation[] annotations = optional.get().getAttribute("value");
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
