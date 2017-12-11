package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxProvider;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.impl.HxAnnotatedImpl;

import java.lang.annotation.Annotation;


/**
 * <br/>Created by a.hofmann on 08.04.2017 at 08:50.
 */
public interface HxElementFactory
  extends HxProvider {

  /**
   * Creates a new modifiable instance with given name
   *
   * @param className of new type instance
   * @return a new and empty instance of <code>HxType</code>
   */
  HxType createType(final String className);

  /**
   * Creates a new reference to an existing type with given name
   *
   * @param className of an existing type
   * @return a new and empty instance of <code>HxType</code>
   */
  HxType createReference(final String className);

  /**
   * Creates a new reference to an existing type
   *
   * @param resolvedType for referencing
   * @return a new and empty instance of <code>HxType</code>
   */
  HxType createReference(final HxType resolvedType);

  /**
   * Creates a new unbound field with given type and name
   *
   * @param type      of new field
   * @param fieldName of new field
   * @return a new unbound field instance
   */
  default HxField createField(final Class<?> type,
                              final String fieldName) {
    return createField(type.getName(), fieldName);
  }

  /**
   * Creates a new unbound field with given type and name
   *
   * @param fieldType      of new field
   * @param fieldName of new field
   * @return a new unbound field instance
   */
  default HxField createField(final HxType fieldType,
                              final String fieldName) {
    return createField(fieldType.getName(), fieldName);
  }

  /**
   * Creates a new unbound field with given type and name
   *
   * @param fieldType  of new field
   * @param fieldName of new field
   * @return a new unbound field instance
   */
  HxField createField(final String fieldType,
                      final String fieldName);

  /**
   * Creates a new unbound constructor with given parameters
   *
   * @param parameterTypes of new constructor
   * @return a new unbound constructor instance
   */
  HxMethod createConstructor(final String... parameterTypes);

  /**
   * Creates a new unbound constructor with given parameters
   *
   * @param parameterTypes of new constructor
   * @return a new unbound constructor instance
   */
  HxMethod createConstructor(final HxType... parameterTypes);

  /**
   * Creates a new unbound constructor with given parameters
   *
   * @param parameterTypes of new constructor
   * @return a new unbound constructor instance
   */
  default HxMethod createConstructor(final Class<?>... parameterTypes) {
    return createConstructor(getHaxxor().toNormalizedClassnames(parameterTypes));
  }

  /**
   * Creates a new unbound constructor-reference with given declaring-type and parameters
   *
   * @param declaringType  of new constructor-reference
   * @param parameterTypes of new constructor-reference
   * @return a new constructor-reference instance
   */
  HxMethod createConstructorReference(final String declaringType,
                                      final String... parameterTypes);

  /**
   * Creates a new unbound method with given name, return-type and parameters
   *
   * @param returnType     of new method
   * @param methodName     of new method
   * @param parameterTypes of new method
   * @return a new unbound method instance
   */
  HxMethod createMethod(final String returnType,
                        final String methodName,
                        final String... parameterTypes);

  /**
   * Creates a new unbound method with given name, return-type and parameters
   *
   * @param returnType     of new method
   * @param methodName     of new method
   * @param parameterTypes of new method
   * @return a new unbound method instance
   */
  HxMethod createMethod(final HxType returnType,
                        final String methodName,
                        final HxType... parameterTypes);

  /**
   * Creates a new unbound method with given name, return-type and parameters
   *
   * @param returnType     of new method
   * @param methodName     of new method
   * @param parameterTypes of new method
   * @return a new unbound method instance
   */
  default HxMethod createMethod(final Class<?> returnType,
                                final String methodName,
                                final Class<?>... parameterTypes) {
    return createMethod(returnType.getName(),
                        methodName,
                        getHaxxor().toNormalizedClassnames(parameterTypes));
  }

  /**
   * Creates a new unbound method-reference with given name, return-type and parameters
   *
   * @param declaringType  of new method-reference
   * @param returnType     of new method-reference
   * @param methodName     of new method-reference
   * @param parameterTypes of new method-reference
   * @return a new method-reference instance
   */
  default HxMethod createMethodReference(final Class<?> declaringType,
                                         final Class<?> returnType,
                                         final String methodName,
                                         final Class<?>... parameterTypes) {
    return createMethodReference(
      declaringType.getName(),
      returnType.getName(),
      methodName,
      getHaxxor().toNormalizedClassnames(parameterTypes)
    );
  }

  /**
   * Creates a new unbound method-reference with given name, return-type and parameters
   *
   * @param declaringType  of new method-reference
   * @param returnType     of new method-reference
   * @param methodName     of new method-reference
   * @param parameterTypes of new method-reference
   * @return a new method-reference instance
   */
  HxMethod createMethodReference(final String declaringType,
                                 final String returnType,
                                 final String methodName,
                                 final String... parameterTypes);

  /**
   * Creates a new unbound parameter with given type
   *
   * @param className of new parameter
   * @return a new unbound parameter instance
   */
  HxParameter createParameter(final String className);

  /**
   * Creates a new unbound parameter with given type
   *
   * @param cls of new parameter
   * @return a new unbound parameter instance
   */
  default HxParameter createParameter(final Class<?> cls) {
    return createParameter(cls.getName());
  }

  /**
   * Creates a new unbound parameter with given type
   *
   * @param parameterType of new parameter
   * @return a new unbound parameter instance
   */
  default HxParameter createParameter(final HxType parameterType) {
    return createParameter(parameterType.getName());
  }

  /**
   * Creates a new unbound annotation with given type and visibility
   *
   * @param className of new annotation
   * @param visible   is the visibility of new annotation
   * @return a new unbound annotation instance
   */
  HxAnnotation createAnnotation(final String className,
                                final boolean visible);

  /**
   * Creates a new unbound annotation with given type and visibility
   *
   * @param cls     of new annotation
   * @param visible is the visibility of new annotation
   * @return a new unbound annotation instance
   */
  default HxAnnotation createAnnotation(final Class<? extends Annotation> cls,
                                        final boolean visible) {
    return createAnnotation(cls.getName(), visible);
  }

  /**
   * Creates a new unbound annotation with given type and visibility
   *
   * @param annotationType of new annotation
   * @param visible        is the visibility of new annotation
   * @return a new unbound annotation instance
   */
  default HxAnnotation createAnnotation(final HxType annotationType,
                                        final boolean visible) {
    return createAnnotation(annotationType.getName(), visible);
  }

  /**
   * Creates a new unbound annotated element
   * @return a new unbound parameter instance
   */
  default HxAnnotated<?> createAnnotated() {
    return new HxAnnotatedImpl<>();
  }
}
