package net.andreho.haxxor.api;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.Optional;

import static net.andreho.haxxor.api.HxTypeTestUtils.checkAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 14.12.2017 at 00:16.
 */
class TestFunctionalityOfHxInheritanceManager
  extends TestBase {

  private Hx hx;

  @BeforeEach
  void setUp() {
    this.hx = Hx.create();
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void getSupertype(Class<?> cls) {
    iterateAlongSuperclasses(cls, hx.resolve(cls), TestBase::compareClassnames);
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void setSupertype(Class<?> cls) {
    HxType type = hx.resolve(cls);
    assertEquals(Number.class.getName(),
                 type.setSupertype(Number.class)
                     .getSupertype().orElseThrow(IllegalStateException::new).getName());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void setSupertypeIsForbidden(Class<?> cls) {
    HxType type = hx.resolve(cls);
    assertThrows(UnsupportedOperationException.class, () -> {
      type.setSupertype(Number.class);
    });
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void hasSuperType(Class<?> cls) {
    iterateAlongSuperclasses(
      cls, hx.resolve(cls),
      (c, t) -> {
        if(c.getSuperclass() != null) {
          assertTrue(t.hasSupertype(c.getSuperclass()));
        }
      }
    );
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void setAnnotatedSupertype(Class<?> cls) {
    HxType type = hx.resolve(cls);
    HxAnnotated<?> annotated = hx.createAnnotated();
    type.setAnnotatedSupertype(annotated);
    assertTrue(type.getAnnotatedSupertype().isPresent());
    assertTrue(type.getAnnotatedSupertype().get() == annotated);
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void setAnnotatedSupertypeIsForbidden(Class<?> cls) {
    HxType type = hx.resolve(cls);
    HxAnnotated<?> annotated = hx.createAnnotated();
    assertThrows(UnsupportedOperationException.class, () -> type.setAnnotatedSupertype(annotated));
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void getAnnotatedSupertype(Class<?> cls) {
    HxType type = hx.resolve(cls);
    assertFalse(type.getAnnotatedSupertype().isPresent());
  }

  @ParameterizedTest
  @MethodSource({JAVA8_CLASSES})
  void getAnnotatedSupertypeJava8(Class<?> cls) {
    HxType type = hx.resolve(cls);
    AnnotatedType annotatedSuperclass = cls.getAnnotatedSuperclass();
    Optional<HxAnnotated<?>> annotatedSupertypeOpt = type.getAnnotatedSupertype();
    if(annotatedSuperclass != null && annotatedSuperclass.getDeclaredAnnotations().length > 0) {
      assertTrue(annotatedSupertypeOpt.isPresent());
      HxAnnotated<?> annotated = annotatedSupertypeOpt.get();
      for(Annotation annotation : annotatedSuperclass.getDeclaredAnnotations()) {
        Optional<HxAnnotation> annotationOpt = annotated.getAnnotation(annotation.annotationType());
        assertTrue(annotationOpt.isPresent());
        checkAnnotations(annotation, annotationOpt.get());
      }
    } else {
      assertFalse(annotatedSupertypeOpt.isPresent());
    }
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void getInterfaces(final Class<?> cls) {
    Class<?> current = cls;
    while(current != null) {
      final HxType type = hx.resolve(current);
      final Class<?>[] classInterfaces = current.getInterfaces();
      final List<HxType> typeInterfaces = type.getInterfaces();

      assertEquals(classInterfaces.length, typeInterfaces.size());

      for(int i = 0; i < classInterfaces.length; i++) {
        final Class<?> clsItf = classInterfaces[i];
        final HxType typeItf = typeInterfaces.get(i);
        assertEquals(clsItf.getName(), typeItf.getName());
        getInterfaces(clsItf);
      }
      current = current.getSuperclass();
    }
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void setInterfaces(Class<?> cls) {
    final HxType type = hx.resolve(cls);
    List<HxType> interfaces = type.setInterfaces(Cloneable.class, Serializable.class).getInterfaces();
    assertEquals(2, interfaces.size());
    assertEquals(Cloneable.class.getName(), interfaces.get(0).getName());
    assertEquals(Serializable.class.getName(), interfaces.get(1).getName());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void setInterfacesIsForbidden(Class<?> cls) {
    final HxType type = hx.resolve(cls);
    assertThrows(UnsupportedOperationException.class, ()->
      type.setInterfaces(Cloneable.class, Serializable.class)
    );
  }

  @Test
  void setInterfaces1() {
  }

  @Test
  void setInterfaces2() {
  }

  @Test
  void setInterfaces3() {
  }

  @Test
  void addInterface() {
  }

  @Test
  void addInterface1() {
  }

  @Test
  void addInterface2() {
  }

  @Test
  void hasInterface() {
  }

  @Test
  void hasInterface1() {
  }

  @Test
  void hasInterface2() {
  }

  @Test
  void indexOfInterface() {
  }

  @Test
  void indexOfInterface1() {
  }

  @Test
  void indexOfInterface2() {
  }

  @Test
  void removeInterfaceAt() {
  }

  @Test
  void removeInterface() {
  }

  @Test
  void removeInterface1() {
  }

  @Test
  void removeInterface2() {
  }

  @Test
  void setAnnotatedInterface() {
  }

  @Test
  void getAnnotatedInterface() {
  }

  @Test
  void removeAnnotatedInterface() {
  }

  @Test
  void clearInterfaces() {
  }

  @Test
  void types() {
  }

  @Test
  void types1() {
  }

  @Test
  void interfaces1() {
  }
}