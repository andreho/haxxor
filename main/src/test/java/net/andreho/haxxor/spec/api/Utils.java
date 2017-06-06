package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Haxxor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
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
public abstract class Utils {

  public static <P extends HxParameterizable<P>>
  void checkParameters(final Parameter[] parameters,
                       final List<HxParameter<P>> hxParameters) {
    assertEquals(parameters.length, hxParameters.size(), "Invalid parameters length.");
    for (int i = 0; i < parameters.length; i++) {
      final Parameter parameter = parameters[i];
      final HxParameter<P> hxParameter = hxParameters.get(i);
      final Haxxor haxxor = hxParameter.getHaxxor();

      checkParameter(parameter, hxParameter, haxxor);
    }
  }

  public static <P extends HxParameterizable<P>> void checkParameter(final Parameter parameter,
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

    checkAnnotatedElementsForEquality(parameter, hxParameter);
  }

  public static void checkClassesForEquality(final Class<?>[] classes,
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
    Set<HxField.Modifiers> givenModifiers = HxField.Modifiers.toModifiers(modifiers);
    Set<HxField.Modifiers> hxModifiers = HxField.Modifiers.toModifiers(hxMember.getModifiers());
    assertTrue(hxModifiers.containsAll(givenModifiers), "Modifiers are invalid: " + givenModifiers);
  }

  public static void checkModifiers(final Member member,
                                    final HxMember<?> hxMember) {
    checkModifiers(member.getModifiers(), hxMember);
  }

  public static void checkAnnotationsEquality(final Annotation[] annotations,
                                              final HxAnnotation[] hxAnnotations) {
    assertEquals(annotations.length, hxAnnotations.length);

    for (int i = 0; i < annotations.length; i++) {
      checkAnnotationsEquality(annotations[i], hxAnnotations[i]);
    }
  }

  public static void checkAnnotatedElementsForEquality(final AnnotatedElement annotated,
                                                       final HxAnnotated hxAnnotated) {
    final Annotation[] declaredAnnotations = annotated.getDeclaredAnnotations();
    final Collection<HxAnnotation> hxAnnotations = hxAnnotated.getAnnotations();
    try {
      checkAnnotationsEquality(declaredAnnotations, hxAnnotations.toArray(new HxAnnotation[0]));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void checkAnnotationsEquality(final Annotation annotation,
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
          checkEnumEquality((Enum) value, hxAnnotation.attribute(name));
        } else if (value.getClass()
                        .isArray()) {
          checkArraysEquality(method, value, hxAnnotation, message);
        } else if (value instanceof Annotation) {
          checkAnnotationsEquality((Annotation) value, hxAnnotation.attribute(name));
        } else {
          assertEquals(value, hxAnnotation.attribute(name), message);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void checkArraysEquality(final Method method,
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
      checkEnumArrayEquality((Enum[]) value, annotation.attribute(name));
    } else if (value instanceof Class[]) {
      checkClassArrayEquality((Class[]) value, annotation.attribute(name));
    } else if (value instanceof Annotation[]) {
      checkAnnotationsEquality((Annotation[]) value, annotation.attribute(name));
    }
  }

  public static void checkClassEquality(final Class cls,
                                        final HxType type) {
    assertEquals(cls.getName(), type.getName());
  }

  public static void checkClassArrayEquality(final Class[] classes,
                                             final HxType[] types) {
    assertEquals(classes.length, types.length, "Class arrays have different lengths.");
    for (int i = 0; i < classes.length; i++) {
      checkClassEquality(classes[i], types[i]);
    }
  }

  public static void checkEnumEquality(final Enum e,
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

  public static void checkEnumArrayEquality(final Enum[] enums,
                                            final HxEnum[] hxEnums) {
    assertEquals(enums.length, hxEnums.length, "Enum arrays have different lengths.");
    for (int i = 0; i < enums.length; i++) {
      checkEnumEquality(enums[i], hxEnums[i]);
    }
  }
}
