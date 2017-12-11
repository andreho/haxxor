package net.andreho.haxxor.api.impl;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxInitializablePart;
import net.andreho.haxxor.api.HxMember;
import net.andreho.haxxor.api.HxOwned;
import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.andreho.haxxor.utils.CommonUtils.concat;
import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * Created by a.hofmann on 30.05.2015.
 */
public class HxAnnotatedImpl<A extends HxAnnotated<A> & HxMember<A> & HxOwned<A>>
  extends HxMemberImpl<A>
  implements HxAnnotated<A> {

  private static final HxAnnotation[] EMPTY_ANNOTATIONS_ARRAY = new HxAnnotation[0];
  private static final Class<Repeatable> REPEATABLE_ANNOTATION_CLASS = Repeatable.class;
  protected List<HxAnnotation> annotations = Collections.emptyList();

  public HxAnnotatedImpl() {
    super();
  }

  protected void cloneAnnotationsTo(HxAnnotatedImpl<A> other) {
    if (!getAnnotations().isEmpty()) {
      for (HxAnnotation annotation : other.getAnnotations()) {
        other.addAnnotation(annotation.clone());
      }
    } else {
      other.annotations = DEFAULT_ANNOTATION_COLLECTION;
    }
  }

  private List<HxAnnotation> initializeAnnotations() {
    List<HxAnnotation> annotations = this.annotations;
    if (isUninitialized(annotations)) {
      this.annotations = annotations = new ArrayList<>();
    }
    return annotations;
  }

  @Override
  public A initialize(final HxInitializablePart part) {
    if(part == HxInitializablePart.ANNOTATIONS){
      initializeAnnotations();
    }
    return (A) this;
  }

  @Override
  public List<HxAnnotation> getAnnotations() {
    return this.annotations;
  }

  @Override
  public A clearAnnotations() {
    this.annotations = Collections.emptyList();
    return (A) this;
  }

  @Override
  public A setAnnotations(Collection<HxAnnotation> annotations) {
    if (isUninitialized(annotations) || annotations.isEmpty()) {
      return clearAnnotations();
    }

    initializeAnnotations().clear();

    for (HxAnnotation annotation : annotations) {
      addAnnotation(annotation);
    }
    return (A) this;
  }

  @Override
  public A addAnnotation(final HxAnnotation annotation) {
    checkAnnotationsState(annotation);
    checkAnnotationExistence(annotation);
    initializeAnnotations();

    annotations.add(annotation);
    annotation.setDeclaringMember(this);
    return (A) this;
  }

  private void checkAnnotationsState(final HxAnnotation annotation) {
    if (annotation.getDeclaringMember() != null) {
      throw new IllegalArgumentException(
        "Given annotation was already bound to another annotated element: "+
           annotation.getDeclaringMember());
    }
  }

  private void checkAnnotationExistence(final HxAnnotation annotation) {
    if(isAnnotationPresent(annotation.getType().getName())) {
      throw new IllegalStateException(
        "Invalid attempt to add multiple an annotation with an already existing type: " + annotation.getType());
    }
  }

  @Override
  public A addRepeatableAnnotationIfNeeded(final HxAnnotation annotation, String repeatableAnnotationType) {
    checkAnnotationsState(annotation);
    initializeAnnotations();

    final Optional<HxAnnotation> repeatableAnnotationOptional = this.getAnnotation(repeatableAnnotationType);

    HxAnnotation repeatableAnnotation;
    if (repeatableAnnotationOptional.isPresent()) {
      repeatableAnnotation = repeatableAnnotationOptional.get();
      final HxAnnotation[] values = concat(repeatableAnnotation.getAttribute("value", EMPTY_ANNOTATIONS_ARRAY), annotation);
      repeatableAnnotation.setAttribute("value", values);
    } else {
      Optional<HxAnnotation> currentOptional = getAnnotation(annotation.getType());

      if(!currentOptional.isPresent()) {
        addAnnotation(annotation);
      } else {
        final HxAnnotation currentAnnotation = currentOptional.get();
        removeAnnotation(currentAnnotation);
        repeatableAnnotation =
          annotation.getType().getHaxxor().createAnnotation(repeatableAnnotationType, true);
        repeatableAnnotation.setAttribute("value", new HxAnnotation[]{currentAnnotation});

        addAnnotation(repeatableAnnotation);
        addRepeatableAnnotationIfNeeded(currentAnnotation, repeatableAnnotationType);
        addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationType);
      }
    }
    return (A) this;
  }


  @Override
  public List<HxAnnotated> getSuperAnnotated() {
    return DEFAULT_SUPER_ANNOTATED_COLLECTION;
  }

  @Override
  public List<HxAnnotation> getAnnotationsByType(final String type) {
    HxType repeatableAnnotationType = null;
    HxType repeatableType = null;

    for (HxAnnotation annotation : getAnnotations()) {
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
      return new ArrayList<>(Collections.singletonList(optional.get()));
    }
    return HxAnnotated.DEFAULT_ANNOTATION_COLLECTION;
  }
}
