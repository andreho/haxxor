package net.andreho.haxxor.stub.impl;

import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxConstants;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.stub.Stub;
import net.andreho.haxxor.stub.errors.HxStubException;
import net.andreho.haxxor.stub.errors.HxStubFieldNotFoundException;
import net.andreho.haxxor.stub.errors.HxStubMethodNotFoundException;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by a.hofmann on 10.12.2017 at 14:24.
 */
public class CheckingStubInjector
  implements HxStubInjector {

  static String getPrefixedName(String name,
                                Class<? extends Annotation> annotationType,
                                HxAnnotated<?> member)
  throws HxStubException {
    return getPrefixedName(name, member.getAnnotation(annotationType).orElse(null), member);
  }

  static String getPrefixedName(String name,
                                HxAnnotation annotation,
                                HxAnnotated<?> member)
  throws HxStubException {
    if (annotation == null) {
      return name;
    }
    String prefix = annotation.getAttribute("value", "");
    if (!prefix.isEmpty()) {
      if (!name.startsWith(prefix)) {
        throw new HxStubException("Invalid prefix or name on element: " + member);
      }
      name = name.substring(prefix.length());
    }
    return name;
  }

  @Override
  public boolean injectStub(final HxType target,
                            final HxType stub)
  throws HxStubException {
    if (!fulfillsRequirements(target, stub)) {
      return false;
    }

    target.loadFieldsAndMethods()
          .cacheMethods()
          .cacheFields();

    stub.loadFieldsAndMethods()
        .cacheMethods()
        .cacheFields();

    checkTargetType(target);
    checkStubType(stub);
    checkStubIntegrability(target, stub);

    return true;
  }

  private boolean fulfillsRequirements(final HxType target,
                                       final HxType stub) {
    if (stub.isAnnotationPresent(Stub.class)) {
      HxAnnotation stubAnnotation = stub.getAnnotation(Stub.class).get();
      HxType[] requires = stubAnnotation.getAttribute("requires", HxConstants.EMPTY_TYPE_ARRAY);
      for (HxType required : requires) {
        if (!required.isAssignableFrom(target)) {
          return false;
        }
      }
    }
    return true;
  }

  protected void checkTargetType(final HxType type)
  throws HxStubException {
    if (!type.isSerializable() || type.isInterface()) {
      throw new HxStubException("Given type is invalid and can't be used as a target: " + type);
    }
  }

  protected void checkStubType(final HxType stub)
  throws HxStubException {
    if (!stub.isSerializable() || stub.isInterface() || !stub.hasSuperType(Object.class)) {
      throw new HxStubException("Given type can't be used as a stub: " + stub);
    }
  }

  protected void checkStubIntegrability(final HxType target,
                                        final HxType stub)
  throws HxStubException {
    checkStubFieldsIntegrability(target, stub);
    checkStubMethodsIntegrability(target, stub);
  }

  protected void checkStubFieldsIntegrability(final HxType target,
                                              final HxType stub)
  throws HxStubException {
    for (HxField field : stub.loadFields().getFields()) {
      if (field.isAnnotationPresent(Stub.Field.class)) {
        String name = getPrefixedName(field.getName(), field.getAnnotation(Stub.Field.class).get(), field);
        if (!target.findFieldRecursively(name, field.getType()).isPresent()) {
          throw new HxStubFieldNotFoundException(
            "Given target class '" + target.getName() + "' doesn't declare the given stub field: " + field);
        }
      }
    }
  }

  protected void checkStubMethodsIntegrability(final HxType target,
                                               final HxType stub)
  throws HxStubException {
    for (HxMethod method : stub.loadMethods().getMethods()) {
      checkMethod(target, method);
    }
  }

  private void checkMethod(final HxType target,
                           final HxMethod method)
  throws HxStubException {
    checkClassReference(target, method);
    checkMethodReference(target, method);
    checkMethodOverride(target, method);
  }

  private void checkClassReference(final HxType target,
                                   final HxMethod method)
  throws HxStubException {
    if (method.isAnnotationPresent(Stub.Class.class)) {
      if (!method.getReturnType().hasName(Class.class) ||
          method.getParametersCount() != 0) {
        throw new HxStubMethodNotFoundException(
          "Declared class reference method must return instance of java.lang.Class and " +
          "doesn't consume any parameters: " + method);
      }
    }
  }

  private void checkMethodReference(final HxType target,
                                    final HxMethod method)
  throws HxStubException {
    if (method.isAnnotationPresent(Stub.Method.class)) {
      String name = getPrefixedName(method.getName(), Stub.Method.class, method);
      if (!target.findMethodRecursively(null, name, method.getParameterTypes()).isPresent()) {
        throw new HxStubMethodNotFoundException(
          "Given target class '" + target.getName() + "' doesn't declare the given stub method: " + method);
      }
    }
  }

  private void checkMethodOverride(final HxType target,
                                   final HxMethod method) {
    if (method.isAnnotationPresent(Stub.Override.class)) {
      if (!target.findMethodRecursively(null, method.getName(), method.getParameterTypes()).isPresent()) {
        throw new HxStubMethodNotFoundException(
          "Given target class '" + target.getName() + "' doesn't declare the given stub method: " + method);
      }
    }
  }
}
