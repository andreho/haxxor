package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import net.andreho.haxxor.spec.impl.HxAnnotationImpl;
import net.andreho.haxxor.spec.impl.HxArrayTypeImpl;
import net.andreho.haxxor.spec.impl.HxConstructorImpl;
import net.andreho.haxxor.spec.impl.HxConstructorReferenceImpl;
import net.andreho.haxxor.spec.impl.HxFieldImpl;
import net.andreho.haxxor.spec.impl.HxMethodImpl;
import net.andreho.haxxor.spec.impl.HxMethodReferenceImpl;
import net.andreho.haxxor.spec.impl.HxParameterImpl;
import net.andreho.haxxor.spec.impl.HxTypeImpl;
import net.andreho.haxxor.spec.impl.HxTypeReferenceImpl;
import net.andreho.haxxor.spi.HxElementFactory;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 20:47.
 */
public class DefaultHxElementFactory
    implements HxElementFactory {

  private final Haxxor haxxor;

  public DefaultHxElementFactory(final Haxxor haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor, "Haxxor instance can't be null.");
  }

  @Override
  public HxType createType(final String internalTypeName) {
    if(haxxor.hasResolved(internalTypeName)) {
      return haxxor.resolve(internalTypeName);
    }
    if(internalTypeName.endsWith("[]")) {
      return new HxArrayTypeImpl(haxxor, internalTypeName);
    }
    return new HxTypeImpl(haxxor, internalTypeName);
  }

  @Override
  public HxTypeReference createReference(final String internalTypeName) {
    if(haxxor.hasReference(internalTypeName)) {
      return haxxor.reference(internalTypeName);
    }
    return new HxTypeReferenceImpl(haxxor, internalTypeName);
  }

  @Override
  public HxTypeReference createReference(final HxType resolvedType) {
    return new HxTypeReferenceImpl(haxxor, resolvedType);
  }

  @Override
  public HxField createField(final String fieldName,
                             final String internalTypeName) {
    return new HxFieldImpl(fieldName, haxxor.reference(internalTypeName));
  }

  @Override
  public HxConstructor createConstructor(final String... parameterTypes) {
    return new HxConstructorImpl(haxxor, haxxor.referencesAsArray(parameterTypes));
  }

  @Override
  public HxConstructor createConstructorReference(final String declaringType, final String... parameterTypes) {
    return new HxConstructorReferenceImpl(haxxor, declaringType, parameterTypes);
  }

  @Override
  public HxMethod createMethod(final String methodName, final String returnType, final String... parameterTypes) {
    return new HxMethodImpl(methodName, createReference(returnType), haxxor.referencesAsArray(parameterTypes));
  }

  @Override
  public HxMethod createMethodReference(final String declaringType, final String methodName, final String returnType,
                                        final String... parameterTypes) {
    return new HxMethodReferenceImpl(haxxor, declaringType, methodName, returnType, parameterTypes);
  }

  @Override
  public HxParameter createParameter(final String internalTypeName) {
    return new HxParameterImpl(haxxor.createReference(internalTypeName));
  }

  @Override
  public HxAnnotation createAnnotation(final String internalTypeName, final boolean visible) {
    return new HxAnnotationImpl(haxxor.createReference(internalTypeName), visible);
  }
}