package net.andreho.haxxor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 00:00.
 */
class HaxxorTest {

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
  }

  @Test
  void getReferenceCache() {
  }

  @Test
  void getClassLoader() {
    assertEquals(Haxxor.class.getClassLoader(), haxxor.getClassLoader());
  }

  @Test
  void hasReference() {
    assertTrue(haxxor.hasReference("java/lang/Object"));
  }

  @Test
  void hasResolved() {
    assertTrue(haxxor.hasResolved("V"));
    assertTrue(haxxor.hasResolved("Z"));
    assertTrue(haxxor.hasResolved("B"));
    assertTrue(haxxor.hasResolved("C"));
    assertTrue(haxxor.hasResolved("S"));
    assertTrue(haxxor.hasResolved("I"));
    assertTrue(haxxor.hasResolved("F"));
    assertTrue(haxxor.hasResolved("J"));
    assertTrue(haxxor.hasResolved("D"));
  }

  @Test
  void reference() {
  }

  @Test
  void referencesAsCollection() {
  }

  @Test
  void referencesAsArray() {
  }

  @Test
  void resolve() {
  }

  @Test
  void load() {
  }

  @Test
  void toInternalClassName() {
  }

  @Test
  void createType() {
  }

  @Test
  void createReference() {
  }

  @Test
  void createField() {
  }

  @Test
  void createConstructor() {
  }

  @Test
  void createConstructorReference() {
  }

  @Test
  void createMethod() {
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