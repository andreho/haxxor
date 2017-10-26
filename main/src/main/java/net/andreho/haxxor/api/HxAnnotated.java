package net.andreho.haxxor.api;

import net.andreho.haxxor.utils.CommonUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxAnnotated<A extends HxAnnotated<A>> {

  Map<String, HxAnnotation> DEFAULT_ANNOTATION_MAP = Collections.emptyMap();
  Collection<HxAnnotation> DEFAULT_ANNOTATION_COLLECTION = Collections.emptySet();
  Collection<HxAnnotated> DEFAULT_SUPER_ANNOTATED_COLLECTION = Collections.emptySet();

  /**
   * @return
   */
  default boolean hasAnnotations() {
    return !CommonUtils.isUninitialized(getAnnotations());
  }

  /**
   * Replaces all annotations with given annotation list
   *
   * @param annotations
   * @return
   */
  A setAnnotations(Collection<HxAnnotation> annotations);


  /**
   * @return
   */
  Map<String, HxAnnotation> getAnnotations();

  /**
   * @param annotation
   * @return
   */
  A addAnnotation(HxAnnotation annotation);

  /**
   * @param annotation
   * @param repeatableAnnotationClass
   * @return
   */
  default A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, Class<? extends Annotation> repeatableAnnotationClass) {
    return addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationClass.getName());
  }

  /**
   * @param annotation
   * @param repeatableAnnotationType
   * @return
   */
  default A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, HxType repeatableAnnotationType) {
    return addRepeatableAnnotationIfNeeded(annotation, repeatableAnnotationType.getName());
  }

  /**
   * @param annotation
   * @param repeatableAnnotationClassname
   * @return
   */
  A addRepeatableAnnotationIfNeeded(HxAnnotation annotation, String repeatableAnnotationClassname);

  /**
   * @param type
   * @return
   */
  Collection<HxAnnotation> getAnnotationsByType(String type);

  /**
   * Links the annotations of this element with the annotations of its parent element(s)
   *
   * @return a collection with parent elements if any or {@link Collections#EMPTY_SET}
   */
  Collection<HxAnnotated> getSuperAnnotated();

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate,
                                       boolean recursive);

  /**
   * Replaces all annotations with given annotation map
   *
   * @param annotations
   * @return
   */
  default A setAnnotations(Map<String, HxAnnotation> annotations) {
    return setAnnotations(annotations.values());
  }

  /**
   * Replaces all current annotations with given annotation list
   *
   * @param annotations
   * @return
   */
  default A setAnnotations(HxAnnotation... annotations) {
    return setAnnotations(Arrays.asList(annotations));
  }

  /**
   * @param type
   * @return
   */
  default boolean isAnnotationPresent(String type) {
    return getAnnotation(type).isPresent();
  }

  /**
   * @param type
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(HxType type) {
    return getAnnotation(type.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(Class<? extends Annotation> annotationType) {
    return getAnnotation(annotationType.getName());
  }

  /**
   * @param type
   * @return
   */
  default Optional<HxAnnotation> getAnnotation(String type) {
    final HxAnnotation annotation = getAnnotations().get(type);
    if (annotation != null) {
      return Optional.of(annotation);
    }
    return Optional.empty();
  }

  /**
   * @param annotation
   * @return
   */
  default A removeAnnotation(HxAnnotation annotation) {
    if (equals(annotation.getDeclaringMember())) {
      throw new IllegalArgumentException("Given annotation does't belong to this annotated element: " + annotation);
    }
    String annotationTypename = annotation.getType().getName();
    if (getAnnotations() != DEFAULT_ANNOTATION_MAP) {
      if (null != getAnnotations().remove(annotationTypename)) {
        annotation.setDeclaringMember(null);
      }
    }
    return (A) this;
  }

  /**
   * @param annotationType
   * @return
   */
  default Collection<HxAnnotation> getAnnotationsByType(Class<? extends Annotation> annotationType) {
    return getAnnotationsByType(annotationType.getName());
  }

  /**
   * @param type
   * @return
   */
  default Collection<HxAnnotation> getAnnotationsByType(HxType type) {
    return getAnnotationsByType(type.getName());
  }

  /**
   * @param type
   * @return
   */
  default boolean isAnnotationPresent(HxType type) {
    return isAnnotationPresent(type.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return isAnnotationPresent(annotationType.getName());
  }

  /**
   * @return
   */
  default Collection<HxAnnotation> annotations() {
    return annotations((a) -> true);
  }

  /**
   * @param predicate
   * @return
   */
  default Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate) {
    return annotations(predicate, false);
  }
}
