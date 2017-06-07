package net.andreho.haxxor.spec.api;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.model.AnnotatedBeanWithJava8Features;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.andreho.haxxor.Utils.toClassNames;
import static net.andreho.haxxor.spec.api.HxTypeTestUtils.checkClassArrays;
import static net.andreho.haxxor.spec.api.HxTypeTestUtils.checkConstructors;
import static net.andreho.haxxor.spec.api.HxTypeTestUtils.checkMethods;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 19:42.
 */
@DisplayName("Contract validation for HxType")
class HxTypeTest {

  private static final String TEST_CLASSES = "classes";
  private static final String TEST_ARRAYS = "arrays";
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
      //MinimalBean[].class,
      //int.class
  );

  private static final List<Class<?>> ARRAYS = Arrays.asList(
      ComplexBean.class,
      ComplexBean[].class,
      ComplexBean[][].class,
      ComplexBean[][][].class,

      boolean.class,

      boolean[].class,
      byte[].class,
      short[].class,
      char[].class,
      int[].class,
      float[].class,
      long[].class,
      double[].class,

      boolean[][].class,
      byte[][].class,
      short[][].class,
      char[][].class,
      int[][].class,
      float[][].class,
      long[][].class,
      double[][].class,

      boolean[][][].class,
      byte[][][].class,
      short[][][].class,
      char[][][].class,
      int[][][].class,
      float[][][].class,
      long[][][].class,
      double[][][].class
  );

  private static final String[] FIELDS = {
      "boolean booleanValue",
      "byte byteValue",
      "short shortValue",
      "char charValue",
      "int intValue",
      "float floatValue",
      "long longValue",
      "double doubleValue",
      "java.lang.String stringValue",

      "boolean[] booleanArray",
      "byte[] byteArray",
      "short[] shortArray",
      "char[] charArray",
      "int[] intArray",
      "float[] floatArray",
      "long[] longArray",
      "double[] doubleArray",
      "java.lang.String[] stringArray"
  };
  private Haxxor haxxor;

  private static Collection<Class<?>> shuffledClasses(Collection<Class<?>> classes) {
    ArrayList<Class<?>> list = new ArrayList<>(classes);
    Collections.shuffle(list);
    return list;
  }

  private static Iterable<Arguments> classes() {
    return shuffledClasses(CLASSES).stream()
                            .map(ObjectArrayArguments::create)
                            .collect(Collectors.toList());
  }

  private static Iterable<Arguments> arrays() {
    return shuffledClasses(ARRAYS).stream()
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
    shuffledClasses(CLASSES).stream()
                     .map(Class::getName)
                     .forEach(strings::add);
    return strings;
  }

  private static List<HxMethod> createNewMethods(Haxxor haxxor) {
    return Arrays.asList(
        haxxor.createMethod("boolean", "isDone"),
        haxxor.createMethod("java.lang.String", "getMessage"),
        haxxor.createMethod("void", "setMessage", "java.lang.String"),
        haxxor.createMethod("java.util.List", "find", "java.lang.String[]")
    )
                 .stream()
                 .map(method -> method.setModifiers(HxMethod.Modifiers.PUBLIC, HxMethod.Modifiers.ABSTRACT))
                 .collect(Collectors.toList());
  }

  @BeforeEach
  void setupEnvironment() {
    haxxor = new Haxxor(); //Flags.SKIP_CODE);
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
    for (Class<?> cls : shuffledClasses(CLASSES)) {
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
    for (Class<?> cls : shuffledClasses(CLASSES)) {
      HxType hxType = haxxor.resolve(cls.getName());
      assertEquals(haxxor.toJavaClassName(cls.getName()), hxType.getName());
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
    HxType hxType = haxxor.resolve(cls.getName());
    if(cls.isArray() || cls.isPrimitive()) {
      assertNull(cls.getPackage());
      assertNull(hxType.getPackageName());
    } else {
      assertEquals(cls.getPackage()
                      .getName(), hxType.getPackageName());
    }
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

    checkClassArrays(interfaces, typeInterfaces);
  }

  @Test
  @DisplayName("Change of type's interfaces must work properly")
  void setInterfaces() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    type.setInterfaces(Serializable.class.getName(), Comparable.class.getName(), Runnable.class.getName());
    checkClassArrays(new Class[]{Serializable.class, Comparable.class, Runnable.class}, type.getInterfaces());
    type.setInterfaces(
        Arrays.asList(
            haxxor.referencesAsArray(
                Serializable.class.getName(),
                Comparable.class.getName(),
                Runnable.class.getName())
        )
    );
    checkClassArrays(new Class[]{Serializable.class, Comparable.class, Runnable.class}, type.getInterfaces());
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
  @DisplayName("References must be computed and reported properly")
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
    checkClassArrays(declaredClasses, haxxor.resolve(cls.getName())
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

    for (int i = 0; i < clsFields.length; i++) {
      Field clsField = clsFields[i];
      Optional<HxField> hxFieldOptional = type.findField(clsField.getName());
      assertTrue(hxFieldOptional.isPresent());
      HxField hxField = hxFieldOptional.get();

      assertTrue(type.getFields()
                     .contains(hxField));
      checkFieldsForEquality(clsField, hxField);
    }
  }

  private void checkFieldsForEquality(final Field field,
                                      final HxField hxField) {
    HxTypeTestUtils.checkModifiers(field, hxField);

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
    HxTypeTestUtils.checkAnnotated(field, hxField);
  }

  @Test
  @DisplayName("Addition of fields must work properly")
  void addField() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    for (String line : FIELDS) {
      String[] split = line.split(" ");
      String fieldType = split[0];
      String fieldName = split[1];

      HxField field = haxxor.createField(fieldType, fieldName);
      type.addField(field);
    }

    for (String line : FIELDS) {
      String[] split = line.split(" ");
      String fieldType = split[0];
      String fieldName = split[1];

      Optional<HxField> fieldOptional = type.findField(fieldName);
      assertTrue(fieldOptional.isPresent());
      HxField field = fieldOptional.get();

      assertTrue(type.getFields()
                     .contains(field));
      assertEquals(type, field.getDeclaringMember());

      assertEquals(fieldName, field.getName());
      assertEquals(fieldType, field.getType()
                                   .getName());
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Removal of fields must be rendered properly")
  void removeField(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());

    for (Field field : cls.getDeclaredFields()) {
      final String fieldName = field.getName();

      Optional<HxField> fieldOptional = type.findField(fieldName);
      assertTrue(fieldOptional.isPresent());
      HxField hxField = fieldOptional.get();

      assertEquals(type, hxField.getDeclaringMember());

      type.removeField(hxField);

      assertFalse(type.hasField(fieldName));
      assertNull(hxField.getDeclaringMember());
      assertFalse(type.findField(fieldName)
                      .isPresent());
      assertFalse(type.getFields()
                      .contains(hxField));
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Presence of fields must be rendered properly and equal to those from Reflection-API")
  void hasField(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    for (Field field : cls.getDeclaredFields()) {
      assertTrue(type.hasField(field.getName()));
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Getting fields must return a field-list that is equal to the one from Reflection-API")
  void getFields(final Class<?> cls) {
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

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Setting fields of a type must work properly and follow contract")
  void setFields(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    List<HxField> oldFields = new ArrayList<>(type.getFields());
    List<HxField> newFields = Arrays.asList(
        haxxor.createField("boolean", "done1"),
        haxxor.createField("java.lang.String", "name1"),
        haxxor.createField("java.util.List", "list1")
    );

    type.setFields(newFields);

    assertEquals(newFields, type.getFields());

    for (HxField oldField : oldFields) {
      String oldFieldName = oldField.getName();

      assertFalse(type.hasField(oldFieldName));
      assertNull(oldField.getDeclaringMember());
      assertFalse(type.findField(oldFieldName)
                      .isPresent());
      assertFalse(type.getFields()
                      .contains(oldField));
    }

    for (HxField newField : newFields) {
      String newFieldName = newField.getName();

      assertTrue(type.hasField(newFieldName));
      assertEquals(type, newField.getDeclaringMember());
      assertTrue(type.findField(newFieldName)
                     .isPresent());
      assertTrue(type.getFields()
                     .contains(newField));
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Getting methods must return a method-list that is equal to the one from Reflection-API")
  void getMethods(final Class<?> cls) {
    Method[] clsMethods = cls.getDeclaredMethods();
    HxType hxType = haxxor.resolve(cls.getName());
    List<HxMethod> hxMethods = hxType.getMethods();
    assertEquals(clsMethods.length, hxMethods.size());

    for (int i = 0; i < clsMethods.length; i++) {
      Method clsMethod = clsMethods[i];
      Optional<HxMethod> hxMethodOptional = hxType.findMethod(clsMethod);
      assertTrue(hxMethodOptional.isPresent(), "Method not found: " + clsMethod);
      HxMethod hxMethod = hxMethodOptional.get();

      assertEquals(clsMethod.getName(), hxMethod.getName());
      assertEquals(haxxor.toJavaClassName(clsMethod.getReturnType()
                                                   .getName()),
                   hxMethod.getReturnType()
                           .getName());

      checkMethods(clsMethod, hxMethod);
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Setting methods of a type must work properly and follow contract")
  void setMethods(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    List<HxMethod> oldMethods = new ArrayList<>(type.getMethods());
    List<HxMethod> newMethods = createNewMethods(haxxor);

    type.setMethods(newMethods);

    assertEquals(newMethods, type.getMethods());

    for (HxMethod oldMethod : oldMethods) {
      String oldMethodName = oldMethod.getName();

      boolean hasMethod = type.hasMethod(oldMethod.getReturnType(), oldMethodName,
                                         oldMethod.getParameterTypes());

      assertFalse(hasMethod, "Old method must be removed properly: " + oldMethod);
      assertNull(oldMethod.getDeclaringMember());
      assertFalse(type.findMethod(oldMethod.getReturnType(), oldMethodName,
                                  oldMethod.getParameterTypes())
                      .isPresent());
      assertFalse(type.getMethods()
                      .contains(oldMethod));
    }

    for (HxMethod newMethod : newMethods) {
      String newMethodName = newMethod.getName();

      assertTrue(type.hasMethod(newMethod.getReturnType(), newMethodName,
                                newMethod.getParameterTypes()));

      assertEquals(type, newMethod.getDeclaringMember());
      assertTrue(type.findMethod(newMethod.getReturnType(), newMethodName,
                                 newMethod.getParameterTypes())
                     .isPresent());
      assertTrue(type.getMethods()
                     .contains(newMethod));
    }
  }

  @Test
  @DisplayName("Each 'findMethod' method must return proper value")
  void findMethodsCheck() {
    final Class<ComplexBean> cls = ComplexBean.class;
    final HxType hxType = haxxor.resolve(cls.getName());
    final Collection<Method> methods = new LinkedHashSet<>();

    for (Method method : cls.getDeclaredMethods()) {
      if ("aMethod".equals(method.getName())) {

        Optional<HxMethod> hxMethod0 = hxType.findMethod(method);
        assertTrue(hxMethod0.isPresent());

        final String returnTypeName = method.getReturnType()
                                            .getName();
        final String name = method.getName();
        final String[] parameters = toClassNames(haxxor, method.getParameterTypes());

        Optional<HxMethod> hxMethod1 = hxType.findMethod(
            haxxor.toJavaClassName(returnTypeName),
            name,
            parameters);

        assertTrue(hxMethod1.isPresent());

        Optional<HxMethod> hxMethod2 = hxType.findMethod(
            haxxor.reference(returnTypeName),
            name,
            haxxor.referencesAsArray(parameters)
        );

        assertTrue(hxMethod2.isPresent());

        Optional<HxMethod> hxMethod3 = hxType.findMethod(
            haxxor.reference(returnTypeName),
            name,
            haxxor.referencesAsList(parameters)
        );

        assertTrue(hxMethod3.isPresent());

        assertEquals(hxMethod0.get(), hxMethod1.get());
        assertEquals(hxMethod1.get(), hxMethod2.get());
        assertEquals(hxMethod2.get(), hxMethod3.get());

        checkMethods(method, hxMethod0.get());

        methods.add(method);
      }
    }
    assertFalse(methods.isEmpty());
    assertEquals(methods.size(), hxType.getMethods("aMethod")
                                       .size());
  }

  @Test
  @DisplayName("Addition of new methods must work properly")
  void addMethod() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    List<HxMethod> newMethods = createNewMethods(haxxor);
    for (HxMethod hxMethod : newMethods) {
      type.addMethod(hxMethod);
    }

    for (HxMethod hxMethod : newMethods) {
      Optional<HxMethod> methodOptional =
          type.findMethod(hxMethod);
      assertTrue(methodOptional.isPresent());
      HxMethod method = methodOptional.get();

      assertTrue(type.getMethods()
                     .contains(hxMethod));
      assertEquals(type, hxMethod.getDeclaringMember());

      assertEquals(hxMethod.getName(), method.getName());
      assertEquals(hxMethod.getReturnType(), method.getReturnType());
      assertEquals(hxMethod.getParameters(), method.getParameters());
      assertEquals(hxMethod.getParameterTypes(), method.getParameterTypes());
      assertEquals(hxMethod, method);
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Setting methods of a type must work properly and follow contract")
  void findMethodDirectly(final Class<?> cls) {
    HxType hxType = haxxor.resolve(cls.getName());
    for(Method method : cls.getDeclaredMethods()) {
      String methodDescriptor = Type.getMethodDescriptor(method);
      Optional<HxMethod> optional = hxType.findMethodDirectly(method.getName(), methodDescriptor);
      assertTrue(optional.isPresent());
      checkMethods(method, optional.get());
    }
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
  void isTypeOf() {
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Class- or interface-inheritance must be rendered properly")
  void isAssignableFrom(final Class<?> cls) {
    isAssignableFrom(cls, haxxor.resolve(cls.getName()));
  }

  void isAssignableFrom(final Class<?> type, final HxType hxType) {
    Class<?> current = type;
    while(current != null) {
      HxType hxCurrent = haxxor.reference(current.getName());
      assertTrue(current.isAssignableFrom(type));
      assertTrue(hxCurrent.isAssignableFrom(hxType));

      if(hxType.equals(hxCurrent)) {
        assertTrue(hxType.isAssignableFrom(hxCurrent), hxType + " = " + hxCurrent);
      } else {
        assertFalse(hxType.isAssignableFrom(hxCurrent), hxType + " = " + hxCurrent);
      }

      for(Class<?> itf : current.getInterfaces()) {

        HxType hxInterface = haxxor.reference(itf.getName());

        assertTrue(itf.isAssignableFrom(type), itf + " = " + type);
        assertTrue(hxInterface.isAssignableFrom(hxType), hxInterface + " = " + hxType);

        assertFalse(type.isAssignableFrom(itf));
        assertFalse(hxType.isAssignableFrom(hxInterface));

        isAssignableFrom(itf, hxType);
      }
      current = current.getSuperclass();
    }
  }

  @ParameterizedTest
  @MethodSource(names = TEST_CLASSES)
  @DisplayName("Class-hierarchy must be rendered properly")
  void checkClassHierarchy(final Class<?> cls) {
    HxType hxType = haxxor.resolve(cls.getName());

    Class<?> enclosingClass = cls.getEnclosingClass();
    HxType enclosingType = hxType.getEnclosingType();

    if(enclosingClass == null) {
      assertNull(enclosingType);
    } else {
      assertEquals(enclosingClass.getName(), enclosingType.getName());
    }

    Method enclosingMethod = cls.getEnclosingMethod();
    HxMethod enclosingHxMethod = hxType.getEnclosingMethod();

    if(enclosingMethod == null) {
      assertNull(enclosingHxMethod);
    } else {
      checkMethods(enclosingMethod, enclosingHxMethod);
    }

    Constructor<?> enclosingConstructor = cls.getEnclosingConstructor();
    HxConstructor enclosingHxConstructor = hxType.getEnclosingConstructor();

    if(enclosingConstructor == null) {
      assertNull(enclosingHxConstructor);
    } else {
      checkConstructors(enclosingConstructor, enclosingHxConstructor);
    }
  }

  private static int getDimension(Class<?> arrayType) {
    int dim = 0;
    while (arrayType.isArray()) {
      arrayType = arrayType.getComponentType();
      dim++;
    }
    return dim;
  }

  @ParameterizedTest
  @MethodSource(names = TEST_ARRAYS)
  @DisplayName("Array-classes must be handled and rendered properly")
  void getComponentType(final Class<?> cls) {
    HxType hxType = haxxor.resolve(cls.getName());
    int dim = 0;
    if(cls.isArray()) {
      Class<?> component = cls;
      HxType hxComponent = hxType;

      while (component.isArray()) {
        assertTrue(hxComponent.isArray());
        assertTrue(hxComponent.getName().endsWith("[]"));
        assertEquals(haxxor.toJavaClassName(component.getName()),
                     hxComponent.getName());

        component = component.getComponentType();
        hxComponent = hxComponent.getComponentType();
        dim++;
      }

      assertEquals(haxxor.toJavaClassName(component.getName()),
                   hxComponent.getName());
    } else {
      assertFalse(hxType.getName().contains("[") || hxType.getName().contains("]"));
      assertFalse(hxType.isArray());
      assertNull(hxType.getComponentType());
    }
    assertEquals(dim, hxType.getDimension());
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
  void toReference() {
  }

  @Test
  void toByteArray() {
  }

  @Test
  @DisplayName("Support of Java 8 features for annotation's placements must be supported and rendered well")
  void java8Annotations() {
    HxType type = haxxor.resolve(AnnotatedBeanWithJava8Features.class.getName());
  }

}