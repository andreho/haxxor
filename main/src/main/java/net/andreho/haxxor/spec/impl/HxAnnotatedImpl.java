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

import static net.andreho.haxxor.Utils.concat;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotatedImpl<A extends HxAnnotated<A> & HxMember<A> & HxOwned<A>>
  extends HxMemberImpl<A>
  implements HxAnnotated<A> {

  private static final HxAnnotation[] EMPTY_ANNOTATIONS_ARRAY = new HxAnnotation[0];
  private static final Class<Repeatable> REPEATABLE_ANNOTATION_CLASS = Repeatable.class;
  protected Map<String, HxAnnotation> annotations = DEFAULT_ANNOTATION_MAP;

  public HxAnnotatedImpl() {
    super();
  }

  protected void cloneAnnotationsTo(HxAnnotatedImpl<A> other) {
    if (!getAnnotations().isEmpty()) {
      for (HxAnnotation annotation : other.getAnnotations().values()) {
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
    if (Utils.isUninitialized(annotations)) {
      this.annotations = DEFAULT_ANNOTATION_MAP;
      return (A) this;
    }

    final Map<String, HxAnnotation> annotationsMap = initializeAnnotationsMap();
    annotationsMap.clear();

    for (HxAnnotation annotation : annotations) {
      annotationsMap.putIfAbsent(annotation.getType().getName(), annotation);
    }

    return (A) this;
  }

  private Map<String, HxAnnotation> initializeAnnotationsMap() {
    Map<String, HxAnnotation> annotationsMap = this.annotations;
    if (annotationsMap == DEFAULT_ANNOTATION_MAP) {
      this.annotations = annotationsMap = new LinkedHashMap<>();
    }
    return annotationsMap;
  }

  @Override
  public A addAnnotation(final HxAnnotation annotation) {
    checkAnnotationsState(annotation);

    final HxAnnotation currentAnnotation = initializeAnnotationsMap()
      .putIfAbsent(annotation.getType().getName(), annotation);

    if(currentAnnotation != null) {
      throw new IllegalStateException(
        "Invalid attempt to add multiple annotations with type: " + annotation.getType());
    }

    annotation.setDeclaringMember(this);

    return (A) this;
  }

  @Override
  public A addRepeatableAnnotationIfNeeded(final HxAnnotation annotation, String repeatableAnnotationType) {
    checkAnnotationsState(annotation);
    final Optional<HxAnnotation> repeatableAnnotationOptional = this.getAnnotation(repeatableAnnotationType);

    HxAnnotation repeatableAnnotation;
    if(repeatableAnnotationOptional.isPresent()) {
      repeatableAnnotation = repeatableAnnotationOptional.get();
      final HxAnnotation[] values = concat(repeatableAnnotation.getAttribute("value", EMPTY_ANNOTATIONS_ARRAY), annotation);
      repeatableAnnotation.attribute("value", values);
    } else {
      final HxAnnotation currentAnnotation = initializeAnnotationsMap()
        .putIfAbsent(annotation.getType().getName(), annotation);

      if(currentAnnotation == null) {
        currentAnnotation.setDeclaringMember(this);
      } else {
        removeAnnotation(currentAnnotation);
        repeatableAnnotation =
          annotation.getType().getHaxxor().createAnnotation(repeatableAnnotationType, true);
        repeatableAnnotation.attribute("value", new HxAnnotation[]{currentAnnotation});

        addAnnotation(repeatableAnnotation);
        addRepeatableAnnotationIfNeeded(currentAnnotation, repeatableAnnotationType);
        addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationType);
      }
    }

    return (A) this;
  }

  private void checkAnnotationsState(final HxAnnotation annotation) {
    if (annotation.getDeclaringMember() != null) {
      throw new IllegalArgumentException("Given annotation was already bound to another host: "+annotation.getDeclaringMember());
    }
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
    if (annotation != null) {
      return Optional.of(annotation);
    }
    return Optional.empty();
  }

  @Override
  public Collection<HxAnnotation> annotations(final Predicate<HxAnnotation> predicate,
                                              boolean recursive) {
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
  public Collection<HxAnnotation> getAnnotationsByType(final String type) {
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
    }

    final Optional<HxAnnotation> optional = getAnnotation(type);
    if (optional.isPresent()) {
      return new ArrayList<>(Arrays.asList(optional.get()));
    }

    return Collections.emptySet();
  }
}
