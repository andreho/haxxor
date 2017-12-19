package net.andreho.haxxor.api;

import net.andreho.haxxor.Debugger;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 13.12.2017 at 17:37.
 */
class TestFunctionalityOfHxType extends TestBase {
  private Hx hx;

  @BeforeEach
  void setUp() {
    this.hx = Hx.create();
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void getVersion(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertEquals(Version.V1_8, type.getVersion());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void setVersionIsForbidden(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertThrows(IllegalStateException.class, () -> type.setVersion(Version.V1_8));
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void setVersion(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    type.setVersion(Version.V1_1);
    assertEquals(Version.V1_1, type.getVersion());
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void getSourceInfo(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    Optional<HxSourceInfo> sourceInfoOpt = type.getSourceInfo();
    assertTrue(sourceInfoOpt.isPresent());
    HxSourceInfo sourceInfo = sourceInfoOpt.get();
    assertEquals(sourceInfo.getSource(), cls.getSimpleName() + ".java");
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void setSourceInfo(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    type.setSourceInfo(HxSourceInfo.createSourceInfo("test.java", "debug-info"));

    Optional<HxSourceInfo> sourceInfoOpt = type.getSourceInfo();
    assertTrue(sourceInfoOpt.isPresent());
    HxSourceInfo sourceInfo = sourceInfoOpt.get();
    assertEquals("test.java", sourceInfo.getSource());
    assertEquals("debug-info", sourceInfo.getDebug());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void setSourceInfoIsForbidden(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertThrows(IllegalStateException.class,
                 () -> type.setSourceInfo(HxSourceInfo.createSourceInfo("test.java", "debug-info")));
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, INTERFACES, CLASSES})
  void getName(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertEquals(cls.getName(), type.getName());
  }

  @ParameterizedTest
  @MethodSource({ARRAYS})
  void getNameOfArrays(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertEquals(cls.getName(), transformToArrayClassname(type));
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS})
  void isReference(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertTrue(type.isReference());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void isReferencePerRequest(Class<?> cls) {
    final HxType type = hx.reference(cls.getName());
    assertTrue(type.isReference());
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void isNotReference(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    assertFalse(type.isReference());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void toClass(Class<?> cls)
  throws ClassNotFoundException {
    final HxType type = hx.resolve(cls.getName());
    assertTrue(cls == type.toClass());
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void wasAlreadyLoadedWith(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    if(cls.getClassLoader() == null) {
      return;
    }
    assertTrue(type.wasAlreadyLoadedWith(getClass().getClassLoader()));
  }

  @ParameterizedTest
  @MethodSource({PRIMITIVES, ARRAYS, INTERFACES, CLASSES})
  void toReference(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    if(type.isReference()) {
      assertTrue(type == type.toReference());
    } else {
      assertTrue(type.toReference().isReference());
    }
  }

  @ParameterizedTest
  @MethodSource({INTERFACES, CLASSES})
  void toByteCode(Class<?> cls) {
    final HxType type = hx.resolve(cls.getName());
    byte[] byteCode = type.toByteCode();
    assertNotNull(byteCode);

    StringWriter original = new StringWriter();
    Debugger.trace(cls, new PrintWriter(original));
    StringWriter made = new StringWriter();
    Debugger.trace(byteCode, new PrintWriter(made));

    String originalCode = original.toString();
    String actualCode = made.toString();

    if(hasDifference(originalCode, actualCode)) {
      assertEquals(originalCode, actualCode);
    }
  }
}