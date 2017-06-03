package net.andreho.haxxor.spec.api;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.Utils;
import net.andreho.haxxor.model.ComplexBean;
import net.andreho.haxxor.model.EmbeddedClassesBean;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.OverAnnotatedValueBean;
import net.andreho.haxxor.model.SpecificBean;
import net.andreho.haxxor.model.ValueBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 03.06.2017 at 19:42.
 */
@DisplayName("Validation of contract for HxType")
class HxTypeTest {

  public static final String TEST_CLASS_NAME_1 = "net.andreho.test.MyTestBean";
  public static final String TEST_CLASS_NAME_2 = "net.andreho.next.test.MyNextTestBean";

  public static final List<Class<?>> TEST_CLASSES = Arrays.asList(
      MinimalBean.class,
      ComplexBean.class,
      EmbeddedClassesBean.class,
      EmbeddedClassesBean.InnerClass.class,
      EmbeddedClassesBean.StaticNestedClass.class
  );

  public static final List<Class<?>> NESTED_TEST_CLASSES = Arrays.asList(
      EmbeddedClassesBean.InnerClass.class,
      EmbeddedClassesBean.StaticNestedClass.class
  );

  private static Stream<Arguments> testArguments() {
    return Stream.of(
        ObjectArrayArguments.create(MinimalBean.class),
        ObjectArrayArguments.create(SpecificBean.class),
        ObjectArrayArguments.create(ValueBean.class),
        ObjectArrayArguments.create(OverAnnotatedValueBean.class)
    );
  }

  private Haxxor haxxor;

  @BeforeEach
  void setupEnvironment() {
    haxxor = new Haxxor();
  }

  private HxType obtainTestType() {
    return haxxor.createType(TEST_CLASS_NAME_1);
  }

  private HxType obtainNextTestType() {
    return haxxor.createType(TEST_CLASS_NAME_2);
  }

  @ParameterizedTest
  @MethodSource(names = "testArguments")
  void withMethodSource(final Class<?> cls) {
  }

  @Test
  @DisplayName("Specific initialization's part must initialize specific type's collection.")
  void initialize() {
    final HxType type = obtainTestType();

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
    assertTrue(Utils.isUninitialized(supplier.get()));
    type.initialize(part);
    Collection<E> annotations = supplier.get();
    assertFalse(Utils.isUninitialized(annotations));
    type.initialize(part);
    assertTrue(annotations == supplier.get());
  }

  @Test
  @DisplayName("The byte-code version of new types must be set to 1.8")
  void getVersion() {
    assertTrue(obtainTestType().getVersion() == HxType.Version.V1_8);
  }

  @Test
  void getName() {
    for(Class<?> cls : TEST_CLASSES) {
      HxType type = haxxor.resolve(cls.getName());
      assertEquals(cls.getName(), type.getName());
      assertTrue(haxxor == type.getHaxxor());
    }

    HxType type1 = haxxor.createType(TEST_CLASS_NAME_1);
    HxType type2 = haxxor.createType(TEST_CLASS_NAME_2);

    assertEquals(TEST_CLASS_NAME_1, type1.getName());
    assertEquals(TEST_CLASS_NAME_2, type2.getName());
    assertTrue(haxxor == type1.getHaxxor());
    assertTrue(type1.getHaxxor() == type2.getHaxxor());
  }

  @Test
  void getSimpleName() {
  }

  @Test
  void getPackageName() {
  }

  @Test
  void getSuperType() {
  }

  @Test
  void setSuperType() {
  }

  @Test
  void setSuperType1() {
  }

  @Test
  void getInterfaces() {
  }

  @Test
  void setInterfaces() {
  }

  @Test
  void getSlotsCount() {
  }

  @Test
  void isReference() {
  }

  @Test
  void getDeclaredTypes() {
  }

  @Test
  void setDeclaredTypes() {
  }

  @Test
  void addField() {
  }

  @Test
  void updateField() {
  }

  @Test
  void removeField() {
  }

  @Test
  void getField() {
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