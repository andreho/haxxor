package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassNameNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 16:21.
 */
class DefaultHxClassNameNormalizerTest
    implements HxClassNameNormalizer {

  private HxClassNameNormalizer nameProvider;

  @BeforeEach
  void prepare() {
    nameProvider = new DefaultHxClassNameNormalizer();
  }

  @Test
  void primitiveTypesToJavaClassName() {
    assertEquals("void", toNormalizedClassName("void"));
    assertEquals("V", toNormalizedClassName("V"));

    assertEquals("boolean", toNormalizedClassName("boolean"));
    assertEquals("Z", toNormalizedClassName("Z"));

    assertEquals("byte", toNormalizedClassName("byte"));
    assertEquals("B", toNormalizedClassName("B"));

    assertEquals("char", toNormalizedClassName("char"));
    assertEquals("C", toNormalizedClassName("C"));

    assertEquals("short", toNormalizedClassName("short"));
    assertEquals("S", toNormalizedClassName("S"));

    assertEquals("int", toNormalizedClassName("int"));
    assertEquals("I", toNormalizedClassName("I"));

    assertEquals("float", toNormalizedClassName("float"));
    assertEquals("F", toNormalizedClassName("F"));

    assertEquals("long", toNormalizedClassName("long"));
    assertEquals("J", toNormalizedClassName("J"));

    assertEquals("double", toNormalizedClassName("double"));
    assertEquals("D", toNormalizedClassName("D"));
  }

  @Test
  void primitiveArrayTypesToJavaClassName() {
    assertEquals("boolean[][][]", toNormalizedClassName("boolean[][][]"));
    assertEquals("boolean[][][]", toNormalizedClassName("[[[Z"));

    assertEquals("byte[][]", toNormalizedClassName("byte[][]"));
    assertEquals("byte[][]", toNormalizedClassName("[[B"));

    assertEquals("char[]", toNormalizedClassName("char[]"));
    assertEquals("char[]", toNormalizedClassName("[C"));

    assertEquals("short[][][][]", toNormalizedClassName("short[][][][]"));
    assertEquals("short[][][][]", toNormalizedClassName("[[[[S"));

    assertEquals("int[]", toNormalizedClassName("int[]"));
    assertEquals("int[]", toNormalizedClassName("[I"));

    assertEquals("float[][]", toNormalizedClassName("float[][]"));
    assertEquals("float[][]", toNormalizedClassName("[[F"));

    assertEquals("long[]", toNormalizedClassName("long[]"));
    assertEquals("long[]", toNormalizedClassName("[J"));

    assertEquals("double[][]", toNormalizedClassName("double[][]"));
    assertEquals("double[][]", toNormalizedClassName("[[D"));
  }

  @Test
  void objectReferencesToJavaClassName() {
    assertEquals("java.lang.String", toNormalizedClassName("java.lang.String"));
    assertEquals("java.lang.String", toNormalizedClassName("java/lang/String"));
    assertEquals("java.lang.String", toNormalizedClassName("Ljava/lang/String;"));

    assertEquals("java.lang.String[]", toNormalizedClassName("java.lang.String[]"));
    assertEquals("java.lang.String[]", toNormalizedClassName("[Ljava/lang/String;"));

    assertEquals("java.lang.String[][]", toNormalizedClassName("java.lang.String[][]"));
    assertEquals("java.lang.String[][]", toNormalizedClassName("[[Ljava/lang/String;"));

    assertEquals("java.lang.String[][][]", toNormalizedClassName("java.lang.String[][][]"));
    assertEquals("java.lang.String[][][]", toNormalizedClassName("[[[Ljava/lang/String;"));
  }

  @Override
  public String toNormalizedClassName(final String typeName) {
    return nameProvider.toNormalizedClassName(typeName);
  }
}