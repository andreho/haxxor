package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Utils;

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
   * Replaces all annotations with given annotation list
   *
   * @param annotations
   * @return
   */
  A setAnnotations(Collection<HxAnnotation> annotations);

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
   * @return
   */
  Map<String, HxAnnotation> getAnnotations();

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
    if(annotation != null) {
      return Optional.of(annotation);
    }
    return Optional.empty();
  }

  /**
   * @param annotation
   * @return
   */
  default A addAnnotation(HxAnnotation annotation) {
    if (Utils.isUninitialized(getAnnotations())) {
      setAnnotations(Arrays.asList());
    }
    if (annotation.getDeclaringMember() != null) {
      throw new IllegalArgumentException("Given annotation was already bound to another host.");
    }
    getAnnotations().putIfAbsent(annotation.getType().getName(), annotation);

    if (this instanceof HxMember) {
      annotation.setDeclaringMember((HxMember) this);
    }

    return (A) this;
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
   * @param type
   * @return
   */
  Collection<HxAnnotation> getAnnotationsByType(String type);

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
