package net.andreho.haxxor.spi;

import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;

/**
 * <br/>Created by a.hofmann on 08.04.2017 at 08:50.
 */
public interface HxElementFactory {

  /**
   * Creates a new modifiable instance with given name
   * @param className of new type instance
   * @return a new and empty instance of <code>HxType</code>
   */
  HxType createType(final String className);

  /**
   * Creates a new reference to an existing type with given name
   * @param className of an existing type
   * @return a new and empty instance of <code>HxType</code>
   */
  HxTypeReference createReference(final String className);

  /**
   * Creates a new reference to an existing type
   * @param resolvedType for referencing
   * @return a new and empty instance of <code>HxType</code>
   */
  HxTypeReference createReference(final HxType resolvedType);

  /**
   * Creates a new unbound field with given type and name
   * @param className of new field
   * @param fieldName of new field
   * @return a new unbound field instance
   */
  HxField createField(final String className,
                      final String fieldName);

  /**
   * Creates a new unbound constructor with given parameters
   * @param parameterTypes of new constructor
   * @return a new unbound constructor instance
   */
  HxConstructor createConstructor(final String... parameterTypes);

  /**
   * Creates a new unbound constructor-reference with given declaring-type and parameters
   * @param declaringType of new constructor-reference
   * @param parameterTypes of new constructor-reference
   * @return a new constructor-reference instance
   */
  HxConstructor createConstructorReference(final String declaringType, final String... parameterTypes);

  /**
   * Creates a new unbound method with given name, return-type and parameters
   * @param returnType of new method
   * @param methodName of new method
   * @param parameterTypes of new method
   * @return a new unbound method instance
   */
  HxMethod createMethod(final String returnType,
                        final String methodName,
                        final String... parameterTypes);

  /**
   * Creates a new unbound method-reference with given name, return-type and parameters
   * @param declaringType of new method-reference
   * @param returnType of new method-reference
   * @param methodName of new method-reference
   * @param parameterTypes of new method-reference
   * @return a new method-reference instance
   */
  HxMethod createMethodReference(final String declaringType,
                                 final String returnType,
                                 final String methodName,
                                 final String... parameterTypes);

  /**
   * Creates a new unbound parameter with given type
   * @param className of new parameter
   * @return a new unbound parameter instance
   */
  HxParameter createParameter(final String className);

  /**
   * Creates a new unbound annotation with given type and visibility
   * @param className of new annotation
   * @param visible is the visibility of new annotation
   * @return a new unbound parameter instance
   */
  HxAnnotation createAnnotation(final String className,
                                final boolean visible);
}
