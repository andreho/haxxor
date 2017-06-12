package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Haxxor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 05.06.2017 at 03:09.
 */
public abstract class HxTypeTestUtils {

  public static void checkTypes(java.lang.reflect.Type type,
                                 HxGenericElement<?> hxGeneric) {
    if (type == null) {
      //assertNull(hxGeneric);
      return;
    }

    checkUnknownType(Collections.newSetFromMap(new IdentityHashMap<>()), type, hxGeneric);
  }

  public static void checkTypes(TypeVariable<?>[] typeVariables,
                                 List<HxTypeVariable> hxTypeVariables) {
    Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
    assertEquals(typeVariables.length, hxTypeVariables.size(), "Invalid size of list with HxTypeVariable-s.");

    for (int i = 0; i < typeVariables.length; i++) {
      TypeVariable<?> typeVariable = typeVariables[i];
      HxTypeVariable hxTypeVariable = hxTypeVariables.get(i);
      checkTypeVariable(visited, typeVariable, hxTypeVariable);
    }
  }

  public static void checkTypes(java.lang.reflect.Type[] types,
                                 List<HxGenericElement<?>> hxGenerics) {
    Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
    assertEquals(types.length, hxGenerics.size(), "Invalid size of list with HxGeneric-s.");

    for (int i = 0; i < types.length; i++) {
      java.lang.reflect.Type typeVariable = types[i];
      HxGenericElement hxGeneric = hxGenerics.get(i);
      checkUnknownType(visited, typeVariable, hxGeneric);
    }
  }

  private static void checkUnknownType(Set<Object> visited,
                                       java.lang.reflect.Type type,
                                       HxGenericElement<?> hxGeneric) {
    if (visited.add(type)) {
      assertTrue(visited.add(hxGeneric), "Invalid tracking of HxGeneric: " + hxGeneric.toString());
    } else {
      assertFalse(visited.add(hxGeneric), "Invalid tracking of HxGeneric: " + hxGeneric.toString());
      return;
    }

    System.out.println("-> " +type);

    if (type instanceof TypeVariable) {
      assertTrue(hxGeneric instanceof HxTypeVariable, "Expected: HxTypeVariable");
      checkTypeVariable(visited, (TypeVariable) type, (HxTypeVariable) hxGeneric);
    } else if (type instanceof ParameterizedType) {
      assertTrue(hxGeneric instanceof HxParameterizedType, "Expected: HxParameterizedType");
      checkParameterizedType(visited, (ParameterizedType) type, (HxParameterizedType) hxGeneric);
    } else if (type instanceof WildcardType) {
      assertTrue(hxGeneric instanceof HxWildcardType, "Expected: HxWildcardType");
      checkWildcardType(visited, (WildcardType) type, (HxWildcardType) hxGeneric);
    } else if (type instanceof GenericArrayType) {
      assertTrue(hxGeneric instanceof HxGenericArrayType, "Expected: HxGenericArrayType");
      checkGenericArrayType(visited, (GenericArrayType) type, (HxGenericArrayType) hxGeneric);
    }
  }

  private static void checkTypeVariable(Set<Object> visited,
                                        TypeVariable<?> typeVariable,
                                        HxTypeVariable hxTypeVariable) {
    assertEquals(typeVariable.getName(), hxTypeVariable.getName());

    final java.lang.reflect.Type[] bounds = typeVariable.getBounds();
    final HxGenericElement<?> hxClassBound = hxTypeVariable.getClassBound();

    assertEquals(bounds.length,
                 (hxClassBound != null ? 1 : 0) +
                 hxTypeVariable.getInterfaceBounds().size(),
                 "HxTypeVariable has invalid class-bound.");

    int offset = 0;
    if (hxClassBound != null) {
      java.lang.reflect.Type classBound = typeVariable.getBounds()[offset++];
      checkUnknownType(visited, classBound, hxClassBound);
    }
    for (int i = 0, len = hxTypeVariable.getInterfaceBounds()
                                        .size(); i < len; i++) {
      checkUnknownType(visited,
                       bounds[i + offset],
                       hxTypeVariable.getInterfaceBounds().get(i));
    }
  }

  private static void checkParameterizedType(Set<Object> visited,
                                             ParameterizedType parameterizedType,
                                             HxParameterizedType hxParameterizedType) {
    java.lang.reflect.Type rawType = parameterizedType.getRawType();
    HxType hxRawType = hxParameterizedType.getRawType();
    boolean equalNames = Objects.equals(hxRawType.getHaxxor().toNormalizedClassName(((Class) rawType).getName()), hxRawType.getName());
    if(!equalNames) {
      System.out.println("WTF?");
    }
    assertTrue(equalNames, "HxParameterizedType has invalid raw-type: "+hxRawType);

    java.lang.reflect.Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    List<HxGenericElement<?>> hxActualTypeArguments = hxParameterizedType.getActualTypeArguments();

    assertNotNull(actualTypeArguments, "Actual-Arguments of ParameterizedType can't be null.");
    assertNotNull(hxActualTypeArguments, "Actual-Arguments of HxParameterizedType can't be null.");
    assertEquals(actualTypeArguments.length, hxActualTypeArguments.size(),
                 "Actual-Arguments of HxParameterizedType has invalid size.");

    for (int i = 0; i < actualTypeArguments.length; i++) {
      java.lang.reflect.Type actualTypeArgument = actualTypeArguments[i];
      HxGenericElement<?> actualHxTypeArgument = hxActualTypeArguments.get(i);

      checkUnknownType(visited, actualTypeArgument, actualHxTypeArgument);
    }
  }

  private static void checkWildcardType(Set<Object> visited,
                                        WildcardType wildcardType,
                                        HxWildcardType hxWildcardType) {
    java.lang.reflect.Type[] upperBounds = wildcardType.getUpperBounds();
    java.lang.reflect.Type[] lowerBounds = wildcardType.getLowerBounds();

    List<HxGenericElement> hxUpperBounds = hxWildcardType.getUpperBounds();
    List<HxGenericElement> hxLowerBounds = hxWildcardType.getLowerBounds();

    assertNotNull(upperBounds);
    assertNotNull(lowerBounds);

    assertNotNull(hxUpperBounds);
    assertNotNull(hxLowerBounds);

    assertEquals(upperBounds.length, hxUpperBounds.size(), "Wildcard's upper-bound has invalid size.");
    assertEquals(lowerBounds.length, hxLowerBounds.size(), "Wildcard's lower-bound has invalid size.");

    for (int i = 0; i < upperBounds.length; i++) {
      java.lang.reflect.Type upperBound = upperBounds[i];
      HxGenericElement<?> hxUpperBound = hxUpperBounds.get(i);
      checkUnknownType(visited, upperBound, hxUpperBound);
    }

    for (int i = 0; i < lowerBounds.length; i++) {
      java.lang.reflect.Type lowerBound = lowerBounds[i];
      HxGenericElement<?> hxLowerBound = hxLowerBounds.get(i);
      checkUnknownType(visited, lowerBound, hxLowerBound);
    }
  }

  private static void checkGenericArrayType(Set<Object> visited,
                                            GenericArrayType genericArrayType,
                                            HxGenericArrayType hxGenericArrayType) {
    java.lang.reflect.Type genericComponentType = genericArrayType.getGenericComponentType();
    HxGenericElement<?> hxGenericComponentType = hxGenericArrayType.getGenericComponentType();
    assertNotNull(genericComponentType);
    assertNotNull(hxGenericComponentType);

    checkUnknownType(visited, genericComponentType, hxGenericComponentType);
  }

  public static <P extends HxExecutable<P>>
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

  public static void checkMethods(Method method, HxMethod hxMethod) {
    Haxxor haxxor = hxMethod.getHaxxor();
    assertEquals(haxxor.toNormalizedClassName(method.getReturnType().getName()),
                 hxMethod.getReturnType().getName());

    assertEquals(method.getName(), hxMethod.getName(), "Method has invalid name.");

    checkExecutable(method, hxMethod, haxxor);
  }

  public static void checkConstructors(Constructor<?> constructor, HxConstructor hxConstructor) {
    Haxxor haxxor = hxConstructor.getHaxxor();
    checkExecutable(constructor, hxConstructor, haxxor);
  }

  private static void checkExecutable(final Executable executable,
                                      final HxExecutable<?> parameterizable,
                                      final Haxxor haxxor) {
    assertEquals(executable.getParameterCount(), parameterizable.getParametersCount(),
                 "Parameter's count is wrong.");
    assertEquals(executable.getParameterCount(), parameterizable.getParameters().size(),
                 "Size of parameter's list is wrong.");
    assertEquals(executable.getParameterCount(), parameterizable.getParameterTypes().size(),
                 "Size of list with parameter's types is wrong.");

    String givenDescriptor;
    if(executable instanceof Method) {
      givenDescriptor = Type.getMethodDescriptor((Method) executable);
    } else {
      givenDescriptor = Type.getConstructorDescriptor((Constructor<?>) executable);
    }

    assertEquals(givenDescriptor, parameterizable.toDescriptor(),
                 "Invalid descriptor.");
    assertTrue(parameterizable.hasDescriptor(givenDescriptor),
               "Current parameterizable object must have given descriptor.");

    checkAnnotated(executable, parameterizable);
    checkParameters(executable.getParameters(), parameterizable.getParameters());

    final Parameter[] parameters = executable.getParameters();
    final Class<?>[] parameterTypes = executable.getParameterTypes();

    for (int i = 0; i < parameters.length; i++) {
      Parameter parameter = parameters[i];
      HxType hxParameterType = parameterizable.getParameterTypeAt(i);

      assertEquals(haxxor.toNormalizedClassName(parameter.getType().getName()),
                   hxParameterType.getName(), "Parameter's type at "+i+" is invalid.");
      assertEquals(haxxor.toNormalizedClassName(parameterTypes[i].getName()),
                   hxParameterType.getName(), "Type of parameter at "+i+" is invalid.");
    }
  }


  public static <P extends HxExecutable<P>> void checkParameter(final Parameter parameter,
                                                                final HxParameter<P> hxParameter,
                                                                final Haxxor haxxor) {
    assertEquals(parameter.getName(), hxParameter.getName(),
                 "Invalid parameter's name.");

    assertEquals(haxxor.toNormalizedClassName(parameter.getType()
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
    assertEquals(annotation.annotationType().getName(),
                 hxAnnotation.getType().getName(),
                 "Annotation's type is invalid.");
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
