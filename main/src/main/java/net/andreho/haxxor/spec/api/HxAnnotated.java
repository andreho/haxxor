package net.andreho.haxxor.spec.api;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Predicate;

/**
 * Created by a.hofmann on 31.05.2015.
 */
public interface HxAnnotated<A extends HxAnnotated<A>> {

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
  Collection<HxAnnotation> getAnnotations();

  /**
   * Replaces all current annotations with given annotation list
   *
   * @param annotations
   * @return
   */
  default A setAnnotations(HxAnnotation... annotations) {
    return setAnnotations(new LinkedHashSet<>(Arrays.asList(annotations)));
  }

  /**
   * Links the annotations of this element with the annotations of its parent element(s)
   *
   * @return a collection with parent elements if any or {@link Collections#EMPTY_SET}
   */
  Collection<HxAnnotated> getSuperAnnotated();

  /**
   * @param type
   * @return
   */
  Collection<HxAnnotation> getAnnotationsByType(String type);

  /**
   * @param predicate
   * @param recursive
   * @return
   */
  Collection<HxAnnotation> annotations(Predicate<HxAnnotation> predicate, boolean recursive);

  /**
   * @param type
   * @return
   */
  default boolean isAnnotationPresent(String type) {
    return getAnnotation(type) != null;
  }

  /**
   * @param type
   * @return
   */
  default HxAnnotation getAnnotation(String type) {
    for (HxAnnotation annotation : getAnnotations()) {
      if (type.equals(annotation.getType()
                                .getName())) {
        return annotation;
      }
    }
    return null;
  }

  /**
   * @param annotation
   * @return
   */
  default A addAnnotation(HxAnnotation annotation) {
    if (getAnnotations() == Collections.EMPTY_SET) {
      setAnnotations(new LinkedHashSet<>());
    }

    getAnnotations().add(annotation);
    annotation.setDeclaringMember((HxMember) this);

    return (A) this;
  }

  /**
   * @param annotation
   * @return
   */
  default boolean hasAnnotation(HxAnnotation annotation) {
    return getAnnotations().contains(annotation);
  }

  /**
   * @param annotation
   * @return
   */
  default A replaceAnnotation(HxAnnotation annotation) {
    if (getAnnotations() != Collections.EMPTY_SET) {
      final HxAnnotation currentAnnotation = getAnnotation(annotation.getType());
      if (currentAnnotation != null &&
          getAnnotations().remove(currentAnnotation)) {
        return addAnnotation(annotation);
      }
    }
    return (A) this;
  }

  /**
   * @param annotation
   * @return
   */
  default A removeAnnotation(HxAnnotation annotation) {
    if (getAnnotations() != Collections.EMPTY_SET) {
      if (getAnnotations().remove(annotation)) {
        annotation.setDeclaringMember(null);
      }
    }

    return (A) this;
  }

  /**
   * @param type
   * @return
   */
  default HxAnnotation getAnnotation(HxType type) {
    return getAnnotation(type.getName());
  }

  /**
   * @param annotationType
   * @return
   */
  default HxAnnotation getAnnotation(Class<? extends Annotation> annotationType) {
    return getAnnotation(annotationType.getName());
  }

  /**
   * @param repeatableAnnotationType
   * @return
   */
  default Collection<HxAnnotation> getAnnotationsByType(Class<? extends Annotation> repeatableAnnotationType) {
    return getAnnotationsByType(repeatableAnnotationType.getName());
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
