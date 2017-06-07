package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Haxxor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 03:09.
 */
public abstract class HxTypeTestUtils {

  public static <P extends HxExecutable<P>>
  void checkParameters(final Parameter[] parameters,
                       final List<HxParameter<P>> hxParameters) {
    assertEquals(parameters.length, hxParameters.size(), "Invalid parameters length.");
    for (int i = 0; i < parameters.length; i++) {
      final Parameter parameter = parameters[i];
      final HxParameter<P> hxParameter = hxParameters.get(i);
      final Haxxor haxxor = hxParameter.getHaxxor();

      checkParameters(parameter, hxParameter, haxxor);
    }
  }

  public static void checkMethods(Method method, HxMethod hxMethod) {
    Haxxor haxxor = hxMethod.getHaxxor();
    assertEquals(haxxor.toJavaClassName(method.getReturnType().getName()),
                 hxMethod.getReturnType().getName());

    assertEquals(method.getName(), hxMethod.getName());

    checkExecutable(method, hxMethod, haxxor);
  }

  public static void checkConstructors(Constructor<?> constructor, HxConstructor hxConstructor) {
    Haxxor haxxor = hxConstructor.getHaxxor();
    checkExecutable(constructor, hxConstructor, haxxor);
  }

  private static void checkExecutable(final Executable executable,
                                      final HxExecutable<?> parameterizable,
                                      final Haxxor haxxor) {
    assertEquals(executable.getParameterCount(), parameterizable.getParametersCount());
    assertEquals(executable.getParameterCount(), parameterizable.getParameters().size());
    assertEquals(executable.getParameterCount(), parameterizable.getParameterTypes().size());

    String givenDescriptor;
    if(executable instanceof Method) {
      givenDescriptor = Type.getMethodDescriptor((Method) executable);
    } else {
      givenDescriptor = Type.getConstructorDescriptor((Constructor<?>) executable);
    }

    assertEquals(givenDescriptor, parameterizable.toDescriptor());
    assertTrue(parameterizable.hasDescriptor(givenDescriptor));

    checkAnnotated(executable, parameterizable);
    checkParameters(executable.getParameters(), parameterizable.getParameters());

    Parameter[] parameters = executable.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      Parameter parameter = parameters[i];

      HxType hxParameterType = parameterizable.getParameterTypeAt(i);
      assertEquals(haxxor.toJavaClassName(executable.getParameterTypes()[i].getName()),
                   hxParameterType.getName());
      assertEquals(haxxor.toJavaClassName(parameter.getType().getName()),
                   hxParameterType.getName());
    }
  }


  public static <P extends HxExecutable<P>> void checkParameters(final Parameter parameter,
                                                                 final HxParameter<P> hxParameter,
                                                                 final Haxxor haxxor) {
    assertEquals(parameter.getName(), hxParameter.getName(),
                 "Invalid parameter's name.");

    assertEquals(haxxor.toJavaClassName(parameter.getType()
                                                 .getName()), hxParameter.getType()
                                                                         .getName(),
                 "Invalid parameter's type.");

    checkModifiers(parameter.getModifiers(), hxParameter);

    assertEquals(parameter.isImplicit(), hxParameter.isImplicit(),
                 "Implicit flag is wrong: " +
                 parameter.getDeclaringExecutable() + ", " + parameter);
    assertEquals(parameter.isNamePresent(), hxParameter.isNamePresent(),
                 "Name-Present flag is wrong: " +
                 parameter.getDeclaringExecutable() + ", " +
                 parameter);
    assertEquals(parameter.isSynthetic(), hxParameter.isSynthetic(),
                 "Synthetic flag is wrong: " +
                 parameter.getDeclaringExecutable() + ", " + parameter);
    assertEquals(parameter.isVarArgs(), hxParameter.isVarArgs(),
                 "Var-Args flag is wrong: " +
                 parameter.getDeclaringExecutable() + ", " + parameter);

    checkAnnotated(parameter, hxParameter);
  }

  public static void checkClassArrays(final Class<?>[] classes,
                                      final List<HxType> types) {
    assertEquals(classes.length, types
        .size(), "Collections have different sizes.");

    for (int i = 0; i < classes.length; i++) {
      Class<?> originalInterface = classes[i];
      HxType typeInterface = types.get(i);
      assertEquals(originalInterface.getName(), typeInterface.getName());
    }
  }

  public static void checkModifiers(final int modifiers,
                                    final HxMember<?> hxMember) {
    Set<HxField.Modifiers> givenModifiers = HxField.Modifiers.toSet(modifiers);
    Set<HxField.Modifiers> hxModifiers = HxField.Modifiers.toSet(hxMember.getModifiers());
    assertTrue(hxModifiers.containsAll(givenModifiers), "Modifiers are invalid: " + givenModifiers);
  }

  public static void checkModifiers(final Member member,
                                    final HxMember<?> hxMember) {
    checkModifiers(member.getModifiers(), hxMember);
  }

  public static void checkAnnotations(final Annotation[] annotations,
                                      final HxAnnotation[] hxAnnotations) {
    assertEquals(annotations.length, hxAnnotations.length);

    for (int i = 0; i < annotations.length; i++) {
      checkAnnotations(annotations[i], hxAnnotations[i]);
    }
  }

  public static void checkAnnotated(final AnnotatedElement annotated,
                                    final HxAnnotated hxAnnotated) {
    final Annotation[] declaredAnnotations = annotated.getDeclaredAnnotations();
    final Collection<HxAnnotation> hxAnnotations = hxAnnotated.getAnnotations();
    try {
      checkAnnotations(declaredAnnotations, hxAnnotations.toArray(new HxAnnotation[0]));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void checkAnnotations(final Annotation annotation,
                                      final HxAnnotation hxAnnotation) {
    assertEquals(annotation.annotationType()
                           .getName(), hxAnnotation.getType()
                                                   .getName());
    try {

      for (Method method : annotation.annotationType()
                                     .getMethods()) {
        final String name = method.getName();
        if (method.getParameterCount() != 0 ||
            "hashCode".equals(name) ||
            "toString".equals(name) ||
            "annotationType".equals(name)) {
          continue;
        }

        final Object value = method.invoke(annotation);
        final String message = "Invalid attribute's value: " + name;

        if (value instanceof Enum) {
          checkEnums((Enum) value, hxAnnotation.attribute(name));
        } else if (value.getClass()
                        .isArray()) {
          checkArrays(method, value, hxAnnotation, message);
        } else if (value instanceof Annotation) {
          checkAnnotations((Annotation) value, hxAnnotation.attribute(name));
        } else {
          assertEquals(value, hxAnnotation.attribute(name), message);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void checkArrays(final Method method,
                                 final Object value,
                                 final HxAnnotation annotation,
                                 final String message) {
    final String name = method.getName();

    if (value instanceof boolean[]) {
      assertTrue(Arrays.equals((boolean[]) value, annotation.attribute(name)), message);
    } else if (value instanceof byte[]) {
      assertTrue(Arrays.equals((byte[]) value, annotation.attribute(name)), message);
    } else if (value instanceof char[]) {
      assertTrue(Arrays.equals((char[]) value, annotation.attribute(name)), message);
    } else if (value instanceof short[]) {
      assertTrue(Arrays.equals((short[]) value, annotation.attribute(name)), message);
    } else if (value instanceof int[]) {
      assertTrue(Arrays.equals((int[]) value, annotation.attribute(name)), message);
    } else if (value instanceof float[]) {
      assertTrue(Arrays.equals((float[]) value, annotation.attribute(name)), message);
    } else if (value instanceof long[]) {
      assertTrue(Arrays.equals((long[]) value, annotation.attribute(name)), message);
    } else if (value instanceof double[]) {
      assertTrue(Arrays.equals((double[]) value, annotation.attribute(name)), message);
    } else if (value instanceof String[]) {
      assertTrue(Arrays.equals((String[]) value, annotation.attribute(name)), message);
    } else if (value instanceof Enum[]) {
      checkEnumArrays((Enum[]) value, annotation.attribute(name));
    } else if (value instanceof Class[]) {
      checkClassArrays((Class[]) value, (HxType[]) annotation.attribute(name));
    } else if (value instanceof Annotation[]) {
      checkAnnotations((Annotation[]) value, annotation.attribute(name));
    }
  }

  public static void checkClasses(final Class cls,
                                  final HxType type) {
    assertEquals(cls.getName(), type.getName());
  }

  public static void checkClassArrays(final Class[] classes,
                                      final HxType[] types) {
    assertEquals(classes.length, types.length, "Class arrays have different lengths.");
    for (int i = 0; i < classes.length; i++) {
      checkClasses(classes[i], types[i]);
    }
  }

  public static void checkEnums(final Enum e,
                                final HxEnum hxEnum) {
    assertEquals(e.getClass()
                  .getName(),
                 hxEnum.getType()
                       .getName(),
                 "Invalid enum-class: " + hxEnum.getType()
                                                .getName());
    assertEquals(e.name(),
                 hxEnum.getName(),
                 "Invalid enum-name: " + hxEnum.getName());
  }

  public static void checkEnumArrays(final Enum[] enums,
                                     final HxEnum[] hxEnums) {
    assertEquals(enums.length, hxEnums.length, "Enum arrays have different lengths.");
    for (int i = 0; i < enums.length; i++) {
      checkEnums(enums[i], hxEnums[i]);
    }
  }
}
