package net.andreho.haxxor.api;

import net.andreho.asm.org.objectweb.asm.Type;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.model.AnnotatedBeanWithJava8Features;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EmbeddingClassesBean;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.OverAnnotatedValueBean;
import net.andreho.haxxor.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.andreho.haxxor.api.HxTypeTestUtils.checkClassArrays;
import static net.andreho.haxxor.api.HxTypeTestUtils.checkConstructors;
import static net.andreho.haxxor.api.HxTypeTestUtils.checkMethods;
import static org.assertj.core.api.Assertions.assertThat;
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
  private Hx haxxor;

  private static Collection<Class<?>> shuffledClasses(Collection<Class<?>> classes) {
    ArrayList<Class<?>> list = new ArrayList<>(classes);
    Collections.shuffle(list);
    return list;
  }

  private static Iterable<Arguments> classes() {
    return shuffledClasses(CLASSES).stream()
                            .map(Arguments::of)
                            .collect(Collectors.toList());
  }

  private static Iterable<Arguments> arrays() {
    return shuffledClasses(ARRAYS).stream()
                            .map(Arguments::of)
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

  private static List<HxMethod> createNewMethods(Hx haxxor) {
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
    haxxor = Hx.builder().withFlags(Hx.Flags.SKIP_CODE).build(); //Haxxor.Flags.SKIP_CODE
  }

  @Test
  @DisplayName("Specific initialization's part must initialize specific type's collection")
  void initialize() {
    final HxType type = haxxor.createType(TEST_CLASS_NAME_1);

    loop:
    for (InitializablePart part : InitializablePart.values()) {
      switch (part) {
        case ANNOTATIONS: {
          checkInitialization(type, part, () -> type.getAnnotations());
        }
        break;
        case INNER_TYPES: {
          checkInitialization(type, part, type::getInnerTypes);
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
                                                                final InitializablePart part,
                                                                final Supplier<T> supplier) {
    assertTrue(CommonUtils.isUninitialized(supplier.get()));
    type.initialize(part);
    Collection<E> annotations = supplier.get();
    assertFalse(CommonUtils.isUninitialized(annotations));
    type.initialize(part);
    assertTrue(annotations == supplier.get());
  }

  @Test
  @DisplayName("The byte-code version of new types must be set to 1.8")
  void getVersion() {
    assertThat(haxxor.createType(TEST_CLASS_NAME_1)
                     .getVersion()).isEqualTo(Version.V1_8);
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
      assertEquals(haxxor.toNormalizedClassname(cls.getName()), hxType.getName());
    }

    HxType type1 = haxxor.createType(TEST_CLASS_NAME_1);
    HxType type2 = haxxor.createType(TEST_CLASS_NAME_2);

    assertEquals(TEST_CLASS_NAME_1, type1.getName());
    assertEquals(TEST_CLASS_NAME_2, type2.getName());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
  @DisplayName("Computed simple classnames must equal to those from original Reflection-API")
  void getSimpleName(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    assertEquals(cls.getSimpleName(), type.getSimpleName());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_CLASSES)
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
    type1.setSupertype(newSuperType);
    assertNotNull(type1.getSupertype());
    assertEquals(newSuperType, type1.getSupertype().get()
                                    .getName());

    assertTrue(type1.hasSupertype(newSuperType));

    HxType type2 = haxxor.createType(TEST_CLASS_NAME_2);
    type2.setSupertype(haxxor.reference(newSuperType));
    assertNotNull(type2.getSupertype());
    assertEquals(newSuperType, type2.getSupertype().get()
                                    .getName());
    assertTrue(type2.hasSupertype(newSuperType));
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
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
            haxxor.references(
                Serializable.class.getName(),
                Comparable.class.getName(),
                Runnable.class.getName())
        )
    );
    checkClassArrays(new Class[]{Serializable.class, Comparable.class, Runnable.class}, type.getInterfaces());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES_AND_PRIMITIVES)
  @DisplayName("Only long and double primitive types allocates two slots on stack or as local variables")
  void getSlotsCount(String typeName) {
    int slots = ("long".equals(typeName) || "double".equals(typeName)) ? 2 : 1;

    assertEquals(slots, haxxor.reference(typeName)
                              .getSlotSize());
    assertEquals(slots, haxxor.resolve(typeName)
                              .getSlotSize());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES_AND_PRIMITIVES)
  @DisplayName("References must be computed and reported properly")
  void isReference(String typeName) {
    HxType reference = haxxor.reference(typeName);
    HxType type = haxxor.resolve(typeName);

    assertTrue(reference.isReference());
    assertNotNull(type);
    if(!type.isPrimitive() && !type.isArray()) {
      assertFalse(type.isReference());
    }

    reference = type.toReference();

    assertNotNull(reference);
    assertTrue(reference.isReference());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
  @DisplayName("Collection with declared-types must be equal to the analog array from Reflection-API")
  void getDeclaredTypes(final Class<?> cls) {
    final Class<?>[] declaredClasses = cls.getDeclaredClasses();
    Set<String> innerTypes =
      haxxor.resolve(cls.getName()).getInnerTypes()
            .stream()
            .map(HxType::getName)
            .collect(Collectors.toSet());

    Set<String> originallyDeclaredClasses =
      Arrays.stream(declaredClasses).map(Class::getName).collect(Collectors.toSet());

    assertThat(innerTypes).containsAll(originallyDeclaredClasses);
  }

  @Test
  @DisplayName("Change of type's declared-types must work properly")
  void setDeclaredTypes() {
    HxType type = haxxor.createType(TEST_CLASS_NAME_1);
    assertTrue(type.getInnerTypes()
                   .isEmpty());
    type.initialize(InitializablePart.INNER_TYPES)
        .getInnerTypes()
        .add(haxxor.reference(TEST_CLASS_NAME_2));
    assertEquals(1, type.getInnerTypes()
                        .size());
    assertEquals(TEST_CLASS_NAME_2, type.getInnerTypes()
                                        .get(0)
                                        .getName());
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
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
                        .toNormalizedClassname(expected);
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
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_CLASSES)
  @DisplayName("Presence of fields must be rendered properly and equal to those from Reflection-API")
  void hasField(final Class<?> cls) {
    HxType type = haxxor.resolve(cls.getName());
    for (Field field : cls.getDeclaredFields()) {
      assertTrue(type.hasField(field.getName()));
    }
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_CLASSES)
  @DisplayName("Getting methods must return a method-list that is equal to the one from Reflection-API")
  void getMethods(final Class<?> cls) {
    final Method[] clsMethods = cls.getDeclaredMethods();
    final Constructor<?>[] clsConstructors = cls.getDeclaredConstructors();

    HxType hxType = haxxor.resolve(cls.getName());
    List<HxMethod> hxMethods = hxType.getMethods();
    int clinitPresent = hxType.findClassInitializer().isPresent()? 1 : 0;
    assertEquals(clsMethods.length + clsConstructors.length, hxMethods.size() - clinitPresent);

    for (int i = 0; i < clsMethods.length; i++) {
      Method clsMethod = clsMethods[i];
      Optional<HxMethod> hxMethodOptional = hxType.findMethod(clsMethod);
      assertTrue(hxMethodOptional.isPresent(), "Method not found: " + clsMethod);
      HxMethod hxMethod = hxMethodOptional.get();


      assertEquals(clsMethod.getName(), hxMethod.getName());
      assertEquals(haxxor.toNormalizedClassname(clsMethod.getReturnType().getName()),
                   hxMethod.getReturnType().getName());

      checkMethods(clsMethod, hxMethod);
    }

    for (int i = 0; i < clsConstructors.length; i++) {
      Constructor<?> clsConstructor = clsConstructors[i];
      Optional<HxMethod> hxConstructor = hxType.findConstructor(clsConstructor);
      assertTrue(hxConstructor.isPresent(), "Constructor not found: " + clsConstructor);
      HxMethod hxMethod = hxConstructor.get();

      assertEquals("<init>", hxMethod.getName());
      assertEquals("void", hxMethod.getReturnType().getName());

      checkConstructors(clsConstructor, hxMethod);
    }
  }

  @ParameterizedTest
  @MethodSource(TEST_CLASSES)
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
                                  oldMethod.getParameterTypes()).isPresent());

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
        final String[] parameters = haxxor.toNormalizedClassnames(method.getParameterTypes());

        Optional<HxMethod> hxMethod1 = hxType.findMethod(
            haxxor.toNormalizedClassname(returnTypeName),
            name,
            parameters);

        assertTrue(hxMethod1.isPresent());

        Optional<HxMethod> hxMethod2 = hxType.findMethod(
            haxxor.reference(returnTypeName),
            name,
            haxxor.references(parameters)
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
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_CLASSES)
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
  @MethodSource(TEST_ARRAYS)
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
        assertEquals(haxxor.toNormalizedClassname(component.getName()),
                     hxComponent.getName());

        component = component.getComponentType();
        hxComponent = hxComponent.getComponentType().get();
        dim++;
      }

      assertEquals(haxxor.toNormalizedClassname(component.getName()),
                   hxComponent.getName());
    } else {
      assertFalse(hxType.getName().contains("[") || hxType.getName().contains("]"));
      assertFalse(hxType.isArray());
      assertFalse(hxType.getComponentType().isPresent());
    }
    assertEquals(dim, hxType.getDimension());
  }

  @Test
  void distanceTo() {
    HxType _int = haxxor.reference(int.class);
    HxType _double = haxxor.reference(double.class);
    HxType objectArray = haxxor.reference(Object[].class);
    HxType stringArray = haxxor.reference(String[].class);

    HxType number = haxxor.reference(Number.class);
    HxType integer = haxxor.reference(Integer.class);
    HxType serializable = haxxor.reference(Serializable.class);
    HxType stringbuilder = haxxor.reference(StringBuilder.class);
    HxType appendable = haxxor.reference(Appendable.class);

    assertEquals(0, _int.distanceTo(_int));
    assertEquals(0, _double.distanceTo(_double));
    assertEquals(0, integer.distanceTo(integer));
    assertEquals(0, serializable.distanceTo(serializable));
    assertEquals(0, objectArray.distanceTo(objectArray));

    assertEquals(-1, _int.distanceTo(_double));
    assertEquals(-1, _double.distanceTo(_int));

    assertEquals(-1, stringArray.distanceTo(objectArray));
    assertEquals(93, objectArray.distanceTo(stringArray));

    assertEquals(-1, integer.distanceTo(number));
    assertEquals(90, number.distanceTo(integer));

    assertEquals(-1, number.distanceTo(serializable));
    assertEquals(9000, serializable.distanceTo(number));

    assertEquals(-1, integer.distanceTo(serializable));
    assertEquals(18090, serializable.distanceTo(integer));

    assertEquals(-1, stringbuilder.distanceTo(serializable));
    assertEquals(9000, serializable.distanceTo(stringbuilder));

    assertEquals(-1, stringbuilder.distanceTo(appendable));
    assertEquals(27090, appendable.distanceTo(stringbuilder));
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