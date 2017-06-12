package net.andreho.haxxor;

import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.model.ValueBean;
import net.andreho.haxxor.spec.api.HxConstructor;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;
import net.andreho.haxxor.spec.api.HxTypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 00:00.
 */
class HaxxorTest {

  public static final String TEST_BEAN_CLASSNAME = MinimalBean.class.getName();
  private Haxxor haxxor;

  @BeforeEach
  void prepareHaxxorInstance() {
    haxxor = new Haxxor();
  }

  @Test
  void isActive() {
    assertTrue(haxxor.isActive());
  }

  @Test
  void getResolvedCache() {
    assertNotNull(haxxor.getResolvedCache());
  }

  @Test
  void getReferenceCache() {
    assertNotNull(haxxor.getReferenceCache());
  }

  @Test
  void getClassLoader() {
    assertEquals(Haxxor.class.getClassLoader(), haxxor.getClassLoader());
  }

  @Test
  void hasReference() {
    assertTrue(haxxor.hasReference("java.lang.Object"));
    assertTrue(haxxor.hasReference("java/lang/Object"));
    assertTrue(haxxor.hasReference("Ljava/lang/Object;"));
  }

  @Test
  void hasResolved() {
    assertTrue(haxxor.hasResolved("void"));
    assertTrue(haxxor.hasResolved("boolean"));
    assertTrue(haxxor.hasResolved("byte"));
    assertTrue(haxxor.hasResolved("char"));
    assertTrue(haxxor.hasResolved("short"));
    assertTrue(haxxor.hasResolved("int"));
    assertTrue(haxxor.hasResolved("float"));
    assertTrue(haxxor.hasResolved("long"));
    assertTrue(haxxor.hasResolved("double"));

    assertFalse(haxxor.hasResolved("java.lang.Object"));

    HxTypeReference reference = haxxor.reference("java.lang.Object");
    HxType resolvedType = haxxor.resolve("java.lang.Object");

    assertTrue(haxxor.hasResolved("java.lang.Object"));
    assertEquals(resolvedType, haxxor.resolve("java.lang.Object"));
  }

  @Test
  void reference() {
    HxTypeReference reference =
        haxxor.reference(TEST_BEAN_CLASSNAME);
    assertNotNull(reference);
    HxType type = reference.toType();
    assertNotNull(type);
    assertFalse(type instanceof HxTypeReference);
    assertTrue(haxxor.hasReference(TEST_BEAN_CLASSNAME));
  }


  @Test
  void referenceForArray() {
    HxTypeReference reference =
        haxxor.reference(TEST_BEAN_CLASSNAME + "[][]");
    assertNotNull(reference);
    HxType type = reference;
//    HxType type = reference.toType();
    assertNotNull(type);
    assertNotNull(type.getComponentType());
    assertNotNull(type.getComponentType().get().getComponentType().get());
    assertTrue(type.isArray());
    assertEquals(2, type.getDimension());
    assertEquals(TEST_BEAN_CLASSNAME + "[][]", type.getName());
    assertEquals(TEST_BEAN_CLASSNAME + "[]", type.getComponentType().get().getName());
    assertEquals(TEST_BEAN_CLASSNAME + "", type.getComponentType().get().getComponentType().get().getName());
    assertTrue(haxxor.hasReference(TEST_BEAN_CLASSNAME + "[][]"));
    assertTrue(haxxor.hasReference(TEST_BEAN_CLASSNAME + "[]"));
    assertTrue(haxxor.hasReference(TEST_BEAN_CLASSNAME + ""));
  }

  @Test
  void referencesAsList() {
    String first = "java.lang.Object";
    String second = TEST_BEAN_CLASSNAME;
    String third = "L" + ValueBean.class.getName()
                                        .replace('.', '/') + ";";
    List<HxType> list = haxxor.referencesAsList(
        first,
        second,
        third);

     assertTrue(list.size() == 3);

     assertEquals(haxxor.toNormalizedClassName(first), list.get(0).getName());
     assertEquals(haxxor.toNormalizedClassName(second), list.get(1).getName());
     assertEquals(haxxor.toNormalizedClassName(third), list.get(2).getName());
  }

  @Test
  void referencesAsArray() {
    String first = "java.lang.Object";
    String second = TEST_BEAN_CLASSNAME;
    String third = "L" + ValueBean.class.getName()
                                        .replace('.', '/') + ";";
    HxType[] array = haxxor.referencesAsArray(
        first,
        second,
        third);

    assertTrue(array.length == 3);

    assertEquals(haxxor.toNormalizedClassName(first), array[0].getName());
    assertEquals(haxxor.toNormalizedClassName(second), array[1].getName());
    assertEquals(haxxor.toNormalizedClassName(third), array[2].getName());
  }

  @Test
  void resolve() {
    final String typeName = TEST_BEAN_CLASSNAME;
    final HxType type = haxxor.resolve(typeName);
    assertEquals(haxxor.toNormalizedClassName(typeName), type.getName());
    assertTrue(haxxor.hasResolved(typeName));
  }

  @Test
  void createType() {
    String typeName = TEST_BEAN_CLASSNAME;
    HxType type = haxxor.createType(typeName);
    assertNotNull(type);
    assertFalse(type.isReference());
    assertEquals(typeName, type.getName());
    assertEquals(typeName.replace('.','/'), type.toInternalName());
    assertEquals("L"+typeName.replace('.','/')+";", type.toDescriptor());
    assertFalse(haxxor.hasResolved(typeName));
    assertFalse(haxxor.hasReference(typeName));
  }

  @Test
  void createReference() {
    String typeName = TEST_BEAN_CLASSNAME;
    HxTypeReference type = haxxor.createReference(typeName);
    assertNotNull(type);

    assertTrue(type.isReference());
    assertEquals(typeName, type.getName());
    assertEquals(typeName.replace('.','/'), type.toInternalName());
    assertEquals("L"+typeName.replace('.','/')+";", type.toDescriptor());

    assertFalse(haxxor.hasResolved(typeName));
    assertFalse(haxxor.hasReference(typeName));
  }

  @Test
  void createField() {
    HxField field = haxxor.createField(TEST_BEAN_CLASSNAME, "aField");
    assertNotNull(field);
    assertNull(field.getDeclaringMember());
  }

  @Test
  void createConstructor() {
    HxConstructor constructor = haxxor.createConstructor(TEST_BEAN_CLASSNAME);
    assertNotNull(constructor);
    assertNull(constructor.getDeclaringMember());
  }

  @Test
  void createConstructorReference() {
    HxConstructor constructor = haxxor.createConstructorReference(TEST_BEAN_CLASSNAME, TEST_BEAN_CLASSNAME);
    assertNotNull(constructor);
    assertEquals(haxxor.reference(TEST_BEAN_CLASSNAME), constructor.getDeclaringMember());
  }

  @Test
  void createMethod() {
    HxMethod method = haxxor.createMethod("void", TEST_BEAN_CLASSNAME,
                                          TEST_BEAN_CLASSNAME);
    assertNotNull(method);
    assertNull(method.getDeclaringMember());
  }

  @Test
  void createMethodReference() {
  }

  @Test
  void createParameter() {
  }

  @Test
  void createAnnotation() {
  }

}