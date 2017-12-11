package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.api.impl.HxAnnotationImpl;
import net.andreho.haxxor.api.impl.HxFieldImpl;
import net.andreho.haxxor.api.impl.HxMethodImpl;
import net.andreho.haxxor.api.impl.HxMethodReferenceImpl;
import net.andreho.haxxor.api.impl.HxParameterImpl;
import net.andreho.haxxor.api.impl.HxTypeImpl;
import net.andreho.haxxor.api.impl.HxTypeReferenceImpl;
import net.andreho.haxxor.spi.HxElementFactory;
import net.andreho.haxxor.utils.NamingUtils;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 30.05.2017 at 20:47.
 */
public class DefaultHxElementFactory
    implements HxElementFactory {

  private final Hx haxxor;

  public DefaultHxElementFactory(final Hx haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor, "Haxxor instance can't be null.");
  }

  @Override
  public Hx getHaxxor() {
    return haxxor;
  }

  @Override
  public HxType createType(final String classname) {
    if(haxxor.hasResolved(classname)) {
      return haxxor.resolve(classname);
    }
    if(NamingUtils.isArray(classname)) {
      throw new IllegalArgumentException("Given type is an array: "+classname);
    }
    if(NamingUtils.isPrimitive(classname)) {
      throw new IllegalArgumentException("Given type is an primitive: "+classname);
    }
    return new HxTypeImpl(haxxor, classname);
  }

  @Override
  public HxType createReference(final String classname) {
    return new HxTypeReferenceImpl(haxxor, classname);
  }

  @Override
  public HxType createReference(final HxType resolvedType) {
    return new HxTypeReferenceImpl(haxxor, resolvedType);
  }

  @Override
  public HxField createField(final String fieldType,
                             final String fieldName) {
    return new HxFieldImpl(haxxor.reference(fieldType), fieldName);
  }

  @Override
  public HxMethod createConstructor(final String... parameterTypes) {
    return createConstructor(haxxor.references(parameterTypes));
  }

  @Override
  public HxMethod createConstructor(final HxType... parameterTypes) {
    return new HxMethodImpl(HxConstants.CONSTRUCTOR_METHOD_NAME,
                            haxxor.reference(void.class),
                            parameterTypes);
  }

  @Override
  public HxMethod createConstructorReference(final String declaringType, final String... parameterTypes) {
    return new HxMethodReferenceImpl(haxxor,
                                     declaringType,
                                     HxConstants.CONSTRUCTOR_METHOD_NAME,
                                     void.class.getName(), parameterTypes);
  }

  @Override
  public HxMethod createMethod(final String returnType,
                               final String methodName,
                               final String... parameterTypes) {
    return createMethod(haxxor.reference(returnType),
                        methodName,
                        haxxor.references(parameterTypes));
  }

  @Override
  public HxMethod createMethod(final HxType returnType,
                               final String methodName,
                               final HxType... parameterTypes) {
    return new HxMethodImpl(methodName, returnType, parameterTypes);
  }

  @Override
  public HxMethod createMethodReference(final String declaringType,
                                        final String returnType,
                                        final String methodName,
                                        final String... parameterTypes) {
    return new HxMethodReferenceImpl(haxxor, declaringType, methodName, returnType, parameterTypes);
  }

  @Override
  public HxParameter createParameter(final String internalTypeName) {
    return new HxParameterImpl(haxxor.reference(internalTypeName));
  }

  @Override
  public HxAnnotation createAnnotation(final String internalTypeName, final boolean visible) {
    return new HxAnnotationImpl(haxxor.reference(internalTypeName), visible);
  }
}
