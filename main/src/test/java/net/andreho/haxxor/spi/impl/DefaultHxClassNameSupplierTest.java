package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassNameSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 16:21.
 */
class DefaultHxClassNameSupplierTest
    implements HxClassNameSupplier {

  private HxClassNameSupplier nameProvider;

  @BeforeEach
  void prepare() {
    nameProvider = new DefaultHxClassNameSupplier();
  }

  @Test
  void primitiveTypesToJavaClassName() {
    assertEquals("void", toJavaClassName("void"));
    assertEquals("V", toJavaClassName("V"));

    assertEquals("boolean", toJavaClassName("boolean"));
    assertEquals("Z", toJavaClassName("Z"));

    assertEquals("byte", toJavaClassName("byte"));
    assertEquals("B", toJavaClassName("B"));

    assertEquals("char", toJavaClassName("char"));
    assertEquals("C", toJavaClassName("C"));

    assertEquals("short", toJavaClassName("short"));
    assertEquals("S", toJavaClassName("S"));

    assertEquals("int", toJavaClassName("int"));
    assertEquals("I", toJavaClassName("I"));

    assertEquals("float", toJavaClassName("float"));
    assertEquals("F", toJavaClassName("F"));

    assertEquals("long", toJavaClassName("long"));
    assertEquals("J", toJavaClassName("J"));

    assertEquals("double", toJavaClassName("double"));
    assertEquals("D", toJavaClassName("D"));
  }

  @Test
  void primitiveArrayTypesToJavaClassName() {
    assertEquals("boolean[][][]", toJavaClassName("boolean[][][]"));
    assertEquals("boolean[][][]", toJavaClassName("[[[Z"));

    assertEquals("byte[][]", toJavaClassName("byte[][]"));
    assertEquals("byte[][]", toJavaClassName("[[B"));

    assertEquals("char[]", toJavaClassName("char[]"));
    assertEquals("char[]", toJavaClassName("[C"));

    assertEquals("short[][][][]", toJavaClassName("short[][][][]"));
    assertEquals("short[][][][]", toJavaClassName("[[[[S"));

    assertEquals("int[]", toJavaClassName("int[]"));
    assertEquals("int[]", toJavaClassName("[I"));

    assertEquals("float[][]", toJavaClassName("float[][]"));
    assertEquals("float[][]", toJavaClassName("[[F"));

    assertEquals("long[]", toJavaClassName("long[]"));
    assertEquals("long[]", toJavaClassName("[J"));

    assertEquals("double[][]", toJavaClassName("double[][]"));
    assertEquals("double[][]", toJavaClassName("[[D"));
  }

  @Test
  void objectReferencesToJavaClassName() {
    assertEquals("java.lang.String", toJavaClassName("java.lang.String"));
    assertEquals("java.lang.String", toJavaClassName("java/lang/String"));
    assertEquals("java.lang.String", toJavaClassName("Ljava/lang/String;"));

    assertEquals("java.lang.String[]", toJavaClassName("java.lang.String[]"));
    assertEquals("java.lang.String[]", toJavaClassName("[Ljava/lang/String;"));

    assertEquals("java.lang.String[][]", toJavaClassName("java.lang.String[][]"));
    assertEquals("java.lang.String[][]", toJavaClassName("[[Ljava/lang/String;"));

    assertEquals("java.lang.String[][][]", toJavaClassName("java.lang.String[][][]"));
    assertEquals("java.lang.String[][][]", toJavaClassName("[[[Ljava/lang/String;"));
  }

  @Override
  public String toJavaClassName(final String typeName) {
    return nameProvider.toJavaClassName(typeName);
  }
}