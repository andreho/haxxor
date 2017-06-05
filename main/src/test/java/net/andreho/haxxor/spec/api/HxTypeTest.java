package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EmbeddingClassesBean;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.OverAnnotatedValueBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 19:42.
 */
@DisplayName("Contract validation for HxType")
class HxTypeTest {

  private static final String TEST_CLASSES = "classes";
  private static final String TEST_CLASSES_AND_PRIMITIVES = "primitiveAndOtherClasses";
  private static final String TEST_CLASS_NAME_1 = "net.andreho.test.MyTestBean";
  private static final String TEST_CLASS_NAME_2 = "net.andreho.next.test.MyNextTestBean";

  private static final List<Class<?>> CLASSES = Arrays.asList(
      MinimalBean.class,
      ComplexBean.class,
      EmbeddingClassesBean.class,
      EmbeddingClassesBean.InnerClass.class,
      EmbeddingClassesBean.StaticNestedClass.class,
      new EmbeddingClassesBean().getLocalClassFromConstructor(),
      new EmbeddingClassesBean().getLocalClassFromMethod(),
      new EmbeddingClassesBean().getAnonymousClass(),
      OverAnnotatedValueBean.class
  );
  private Haxxor haxxor;

  private static Collection<Class<?>> shuffledClasses() {
    ArrayList<Class<?>> list = new ArrayList<>(CLASSES);
    Collections.shuffle(list);
    return list;
  }

  private static Iterable<Arguments> classes() {
    return shuffledClasses().stream()
                            .map(ObjectArrayArguments::create)
                            .collect(Collectors.toList());
  }

  private static Collection<String> testPrimitiveClasses() {
    return Arrays.asList(
        "boolean",
        "byte",
        "short",
        "char",
        "int",
        "float",
        "long",
        "double"
    );
  }

  private static Collection<String> primitiveAndOtherClasses() {
    ArrayList<String> strings = new ArrayList<>(testPrimitiveClasses());
    shuffledClasses().stream()
                     .map(Class::getName)
                     .forEach(strings::add);
    return strings;
  }

  @BeforeEach
  void setupEnvironment() {
    haxxor = new Haxxor();
  }

  @Test
  @DisplayName("Specific initialization's part must initialize specific type's collection")
  void initialize() {
    final HxType type = haxxor.createType(TEST_CLASS_NAME_1);

    loop:
    for (HxType.Part part : HxType.Part.values()) {
      switch (part) {
        case DEFAULTS: {
          continue loop;
        }
        case ANNOTATIONS: {
          checkInitialization(type, part, type::getAnnotations);
        }
        break;
        case CONSTRUCTORS: {
          checkInitialization(type, part, type::getConstructors);
        }
        break;
        case DECLARED_TYPES: {
          checkInitialization(type, part, type::getDeclaredTypes);
        }
        break;
        case INTERFACES: {
          checkInitialization(type, part, type::getInterfaces);
        }
        break;
        case FIELDS: {
          checkInitialization(type, part, type::getFields);
        }
        break;
        case METHODS: {
          checkInitialization(type, part, type::getMethods);
        }
        break;
        default:
          throw new IllegalStateException("Not reachable");
      }
    }
  }

  private <E, T extends Collection<E>> void checkInitialization(final HxType type,
                                                                final HxType.Part part,
                                                                final Supplier<T> supplier) {
    assertTrue(net.andreho.haxxor.Utils.isUninitialized(supplier.get()));
    type.initialize(part);
    Collection<E> annotations = supplier.get();
    assertFalse(net.andreho.haxxor.Utils.isUninitialized(annotations));
    type.initialize(part);
    assertTrue(annotations == supplier.get());
  }

  @Test
  @DisplayName("The byte-code version of new types must be set to 1.8")
  void getVersion() {
    assertTrue(haxxor.createType(TEST_CLASS_NAME_1)
                     .getVersion() == HxType.Version.V1_8);
  }

  @Test
  @DisplayName("Every created type must belong to exactly one used Haxxor instance")
  void getHaxxor() {
    for (Class<?> cls : shuffledClasses()) {
      checkOwnership(haxxor.resolve(cls.getName()));
    }

    checkOwnership(haxxor.createType(TEST_CLASS_NAME_1));
    checkOwnership(haxxor.createReference(TEST_CLASS_NAME_2));
  }

  private void checkOwnership(final HxType type) {
    assertTrue(haxxor == type.getHaxxor(), "Type doesn't belong to given Haxxor instance: " + type);
  }

  @Test
  @DisplayName("Computed classnames must equal to those from original Reflection-API")
  void getName() {
    for (Class<?> cls : shuffledClasses()) {
      HxType type = haxxor.resolve(cls.getName());
      assertEquals(cls.getName(), type.getName());
    }

    HxType type1 = haxxor.createType(TEST_CLASS_NAME_1);
    HxType type2 = haxxor.createType(TEST_CLASS_NAME_2);

    assertEquals(TEST_CLASS_NAME_1, type1.getName());
    assertEquals(TEST_CLASS_NAME_2, type2.getName());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Computed simple classnames must equal to those from original Reflection-API")
  void getSimpleName(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    assertEquals(cls.getSimpleName(), type.getSimpleName());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Computed package-names must equal to those from original Reflection-API")
  void getPackageName(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    assertEquals(cls.getPackage()
                    .getName(), type.getPackageName());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Computed super-types must equal to those from original Reflection-API")
  void getSuperType(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    assertEquals(cls.getPackage()
                    .getName(), type.getPackageName());
  }

  @Test
  @DisplayName("Changing the supertype must be done properly")
  void setSuperType() {
    final String newSuperType = MinimalBean.class.getName();
    HxType type1 = haxxor.createType(TEST_CLASS_NAME_1);
    type1.setSuperType(newSuperType);
    assertNotNull(type1.getSuperType());
    assertEquals(newSuperType, type1.getSuperType()
                                    .getName());

    HxType type2 = haxxor.createType(TEST_CLASS_NAME_2);
    type2.setSuperType(haxxor.reference(newSuperType));
    assertNotNull(type2.getSuperType());
    assertEquals(newSuperType, type2.getSuperType()
                                    .getName());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Computed interfaces must equal to those from original Reflection-API")
  void getInterfaces(final Class<?> cls) {
    final HxType type = haxxor.resolve(cls.getName());
    final Class<?>[] interfaces = cls.getInterfaces();
    final List<HxType> typeInterfaces = type.getInterfaces();

    checkClassesForEquality(interfaces, typeInterfaces);
  }

  private void checkClassesForEquality(final Class<?>[] classes,
                                       final List<HxType> types) {
    assertEquals(classes.length, types
        .size(), "Collections have different sizes.");

    for (int i = 0; i < classes.length; i++) {
      Class<?> originalInterface = classes[i];
      HxType typeInterface = types.get(i);
      assertEquals(originalInterface.getName(), typeInterface.getName());
    }
  }

  @Test
  @DisplayName("Change of type's interfaces must work properly")
  void setInterfaces() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    type.setInterfaces(Serializable.class.getName(), Comparable.class.getName(), Runnable.class.getName());
    checkClassesForEquality(new Class[]{Serializable.class, Comparable.class, Runnable.class}, type.getInterfaces());
    type.setInterfaces(
        Arrays.asList(
            haxxor.referencesAsArray(
                Serializable.class.getName(),
                Comparable.class.getName(),
                Runnable.class.getName())
        )
    );
    checkClassesForEquality(new Class[]{Serializable.class, Comparable.class, Runnable.class}, type.getInterfaces());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES_AND_PRIMITIVES)
  @DisplayName("Only long and double primitive types allocates two slots on stack or as local variables")
  void getSlotsCount(String typeName) {
    int slots = ("long".equals(typeName) || "double".equals(typeName)) ? 2 : 1;

    assertEquals(slots, haxxor.reference(typeName)
                              .getSlotsCount());
    assertEquals(slots, haxxor.resolve(typeName)
                              .getSlotsCount());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES_AND_PRIMITIVES)
  @DisplayName("Only long and double allocates two slots on stack or as local variables")
  void isReference(String typeName) {
    HxTypeReference reference = haxxor.reference(typeName);
    HxType type = haxxor.resolve(typeName);

    assertTrue(reference.isReference());
    assertNotNull(type);
    assertFalse(type.isReference());

    reference = type.toReference();

    assertNotNull(reference);
    assertTrue(reference.isReference());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Collection with declared-types must be equal to the analog array from Reflection-API")
  void getDeclaredTypes(final Class<?> cls) {
    final Class<?>[] declaredClasses = cls.getDeclaredClasses();
    checkClassesForEquality(declaredClasses, haxxor.resolve(cls.getName())
                                                   .getDeclaredTypes());
  }

  @Test
  @DisplayName("Change of type's declared-types must work properly")
  void setDeclaredTypes() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    assertTrue(type.getDeclaredTypes()
                   .isEmpty());
    type.initialize(HxType.Part.DECLARED_TYPES)
        .getDeclaredTypes()
        .add(haxxor.reference(TEST_CLASS_NAME_2));
    assertEquals(1, type.getDeclaredTypes()
                        .size());
    assertEquals(TEST_CLASS_NAME_2, type.getDeclaredTypes()
                                        .get(0)
                                        .getName());
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Computed fields must be equal to those from Reflection-API")
  void getField(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    Field[] clsFields = cls.getDeclaredFields();
    List<HxField> typeFields = type.getFields();

    assertEquals(clsFields.length, typeFields.size());

    for (int i = 0; i < clsFields.length; i++) {
      Field clsField = clsFields[i];
      HxField hxField = typeFields.get(i);
      checkFieldsForEquality(clsField, hxField);
    }
  }

  private void checkFieldsForEquality(final Field field,
                                      final HxField hxField) {
    Utils.checkModifiers(field, hxField);

    assertEquals(field.getName(), hxField.getName());

    String expected = field.getType()
                           .getName();

    String actual = hxField.getType()
                           .getName();

    if (expected.startsWith("[")) {
      expected = hxField.getHaxxor()
                        .toJavaClassName(expected);
    }

    assertEquals(expected, actual);

    try {
      Utils.checkAnnotatedElementsForEquality(field, hxField);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }

  @Test
  @DisplayName("Change of type's declared-types must work properly")
  void addField() {
  }

  @Test
  void updateField() {
  }

  @Test
  void removeField() {
  }


  @Test
  void hasField() {
  }

  @Test
  void getFields() {
  }

  @Test
  void setFields() {
  }

  @Test
  void getMethods() {
  }

  @Test
  void setMethods() {
  }

  @Test
  void getMethods1() {
  }

  @Test
  void addMethod() {
  }

  @Test
  void getMethod() {
  }

  @Test
  void getMethodDirectly() {
  }

  @Test
  void getMethod1() {
  }

  @Test
  void getMethod2() {
  }

  @Test
  void getMethod3() {
  }

  @Test
  void hasMethod() {
  }

  @Test
  void hasMethod1() {
  }

  @Test
  void hasMethod2() {
  }

  @Test
  void getConstructors() {
  }

  @Test
  void setConstructors() {
  }

  @Test
  void addConstructor() {
  }

  @Test
  void getConstructorWithParameters() {
  }

  @Test
  void getConstructor() {
  }

  @Test
  void getConstructor1() {
  }

  @Test
  void getConstructor2() {
  }

  @Test
  void hasConstructor() {
  }

  @Test
  void hasConstructor1() {
  }

  @Test
  void isGeneric() {
  }

  @Test
  void getGenericSignature() {
  }

  @Test
  void setGenericSignature() {
  }

  @Test
  void getGenericSuperType() {
  }

  @Test
  void getGenericInterfaces() {
  }

  @Test
  void is() {
  }

  @Test
  void isTypeOf() {
  }

  @Test
  void isAssignableFrom() {
  }

  @Test
  void fields() {
  }

  @Test
  void fields1() {
  }

  @Test
  void fields2() {
  }

  @Test
  void methods() {
  }

  @Test
  void methods1() {
  }

  @Test
  void methods2() {
  }

  @Test
  void constructors() {
  }

  @Test
  void constructors1() {
  }

  @Test
  void constructors2() {
  }

  @Test
  void types() {
  }

  @Test
  void types1() {
  }

  @Test
  void types2() {
  }

  @Test
  void interfaces() {
  }

  @Test
  void interfaces1() {
  }

  @Test
  void interfaces2() {
  }

  @Test
  void getEnclosingType() {
  }

  @Test
  void getEnclosingMethod() {
  }

  @Test
  void getEnclosingConstructor() {
  }

  @Test
  void getComponentType() {
  }

  @Test
  void isArray() {
  }

  @Test
  void getDimension() {
  }

  @Test
  void isPrimitive() {
  }

  @Test
  void isLocalType() {
  }

  @Test
  void isMemberType() {
  }

  @Test
  void isFinal() {
  }

  @Test
  void isPublic() {
  }

  @Test
  void isProtected() {
  }

  @Test
  void isPrivate() {
  }

  @Test
  void isInternal() {
  }

  @Test
  void isAbstract() {
  }

  @Test
  void isInterface() {
  }

  @Test
  void isEnum() {
  }

  @Test
  void isAnnotation() {
  }

  @Test
  void isAnonymous() {
  }

  @Test
  void loadClass() {
  }

  @Test
  void loadClass1() {
  }

  @Test
  void toInternalName() {
  }

  @Test
  void toDescriptor() {
  }

  @Test
  void toDescriptor1() {
  }

  @Test
  void toReference() {
  }

  @Test
  void toByteArray() {
  }

}