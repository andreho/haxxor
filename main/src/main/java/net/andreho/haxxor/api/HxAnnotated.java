package net.andreho.haxxor.api;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static net.andreho.haxxor.utils.CommonUtils.isUninitialized;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxAnnotated<A extends HxAnnotated<A>> extends HxInitializable<A> {

  List<HxAnnotation> DEFAULT_ANNOTATION_COLLECTION = Collections.emptyList();
  List<HxAnnotated> DEFAULT_SUPER_ANNOTATED_COLLECTION = Collections.emptyList();

  /**
   * @return
   */
  default boolean hasAnnotations() {
    return !isUninitialized(getAnnotations()) &&
           !getAnnotations().isEmpty();
  }

  /**
   * @return collections with all managed annotations
   */
  default List<HxAnnotation> getAnnotations() {
    return Collections.emptyList();
  }

  /**
   * Replaces all annotations with given annotation list
   *
   * @param annotations
   * @return this instance
   */
  default A setAnnotations(Collection<HxAnnotation> annotations) {
    throw new UnsupportedOperationException("This element can't define any annotations.");
  }

  /**
   * Replaces all current annotations with given annotation list
   *
   * @param annotations
   * @return this instance
   */
  default A setAnnotations(HxAnnotation... annotations) {
    return setAnnotations(Arrays.asList(annotations));
  }

  /**
   * @param annotation to add
   * @return this instance
   */
  default A addAnnotation(HxAnnotation annotation) {
    initialize(HxInitializablePart.ANNOTATIONS)
      .getAnnotations().add(annotation);
    return (A) this;
  }

  /**
   * @param annotation to add
   * @param repeatableAnnotationClass of a repeatable class
   * @return this instance
   */
  default A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, Class<? extends Annotation> repeatableAnnotationClass) {
    return addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationClass.getName());
  }

  /**
   * @param annotation to add
   * @param repeatableAnnotationType of a repeatable class
   * @return this instance
   */
  default A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, HxType repeatableAnnotationType) {
    return addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationType.getName());
  }

  /**
   * @param annotation to add
   * @param repeatableAnnotationClassname of a repeatable class
   * @return this instance
   */
  default A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, String repeatableAnnotationClassname) {
    throw new UnsupportedOperationException("This element can't define any annotations.");
  }

  /**
   * Links the annotations of this element with the annotations of its parent element(s)
   *
   * @return a collection with parent elements if any or {@link Collections#EMPTY_SET}
   */
  default List<HxAnnotated> getSuperAnnotated() {
    return Collections.emptyList();
  }

  /**
   * @param type
   * @return
   */
  default boolean isAnnotationPresent(String type) {
    return getAnnotation(type).isPresent();
  }

  /**
   * @param annotationType to look for
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(HxType annotationType) {
    return getAnnotation(annotationType.getName());
  }

  /**
   * @param annotationType to look for
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(Class<? extends Annotation> annotationType) {
    return getAnnotation(annotationType.getName());
  }

  /**
   * @param className of annotation to look for
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(String className) {
    for(HxAnnotation annotation : getAnnotations()) {
      if(annotation.getType().hasName(className)) {
        return Optional.of(annotation);
      }
    }
    return Optional.empty();
  }

  /**
   * @param annotation
   * @return this instance
   */
  default A removeAnnotation(HxAnnotation annotation) {
    if (equals(annotation.getDeclaringMember())) {
      throw new IllegalArgumentException("Given annotation does't belong to this annotated element: " + annotation);
    }
    final List<HxAnnotation> annotations = getAnnotations();
    final String annotationTypename = annotation.getType().getName();
    for(Iterator<HxAnnotation> iterator = annotations.iterator(); iterator.hasNext(); ) {
      HxAnnotation current = iterator.next();
      if(current.getType().hasName(annotationTypename)) {
        iterator.remove();
        annotation.setDeclaringMember(null);
        break;
      }
    }
    return (A) this;
  }

  /**
   * @return this instance
   */
  default A clearAnnotations() {
    Collection<HxAnnotation> annotations = getAnnotations();
    if(!annotations.isEmpty()) {
      annotations.clear();
    }
    return (A) this;
  }

  /**
   * @param type of annotations to fetch
   * @return
   */
  default List<HxAnnotation> getAnnotationsByType(String type) {
    return Collections.emptyList();
  }

  /**
   * @param annotationType
   * @return
   */
  default List<HxAnnotation> getAnnotationsByType(Class<? extends Annotation> annotationType) {
    return getAnnotationsByType(annotationType.getName());
  }

  /**
   * @param type
   * @return
   */
  default List<HxAnnotation> getAnnotationsByType(HxType type) {
    return getAnnotationsByType(type.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean isAnnotationPresent(HxType annotationType) {
    return isAnnotationPresent(annotationType.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return isAnnotationPresent(annotationType.getName());
  }

  /**
   * @param predicate
   * @return
   */
  default List<HxAnnotation> annotations(Predicate<HxAnnotation> predicate) {
    return annotations(predicate, false);
  }

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  default List<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive) {
    final List<HxAnnotation> annotations = getAnnotations();
    final List<HxAnnotation> foundAnnotations = new ArrayList<>(annotations.size());
    for (HxAnnotation annotation : annotations) {
      if (predicate.test(annotation)) {
        foundAnnotations.add(annotation);
      }
    }
    if (recursive) {
      List<HxAnnotated> superAnnotated = getSuperAnnotated();
      for (HxAnnotated annotated : superAnnotated) {
        Collection<HxAnnotation> subAnnotations = annotated.annotations(predicate, recursive);
        foundAnnotations.addAll(subAnnotations);
      }
    }
    return foundAnnotations;
  }
}
