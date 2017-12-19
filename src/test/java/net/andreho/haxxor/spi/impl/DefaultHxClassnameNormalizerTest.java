package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxClassnameNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 16:21.
 */
class DefaultHxClassnameNormalizerTest
    implements HxClassnameNormalizer {

  private HxClassnameNormalizer nameProvider;

  @BeforeEach
  void prepare() {
    nameProvider = new DefaultHxClassnameNormalizer();
  }

  @Test
  void primitiveTypesToJavaClassName() {
    assertEquals("void", toNormalizedClassname("void"));
    assertEquals("V", toNormalizedClassname("V"));

    assertEquals("boolean", toNormalizedClassname("boolean"));
    assertEquals("Z", toNormalizedClassname("Z"));

    assertEquals("byte", toNormalizedClassname("byte"));
    assertEquals("B", toNormalizedClassname("B"));

    assertEquals("char", toNormalizedClassname("char"));
    assertEquals("C", toNormalizedClassname("C"));

    assertEquals("short", toNormalizedClassname("short"));
    assertEquals("S", toNormalizedClassname("S"));

    assertEquals("int", toNormalizedClassname("int"));
    assertEquals("I", toNormalizedClassname("I"));

    assertEquals("float", toNormalizedClassname("float"));
    assertEquals("F", toNormalizedClassname("F"));

    assertEquals("long", toNormalizedClassname("long"));
    assertEquals("J", toNormalizedClassname("J"));

    assertEquals("double", toNormalizedClassname("double"));
    assertEquals("D", toNormalizedClassname("D"));
  }

  @Test
  void primitiveArrayTypesToJavaClassName() {
    assertEquals("boolean[][][]", toNormalizedClassname("boolean[][][]"));
    assertEquals("boolean[][][]", toNormalizedClassname("[[[Z"));

    assertEquals("byte[][]", toNormalizedClassname("byte[][]"));
    assertEquals("byte[][]", toNormalizedClassname("[[B"));

    assertEquals("char[]", toNormalizedClassname("char[]"));
    assertEquals("char[]", toNormalizedClassname("[C"));

    assertEquals("short[][][][]", toNormalizedClassname("short[][][][]"));
    assertEquals("short[][][][]", toNormalizedClassname("[[[[S"));

    assertEquals("int[]", toNormalizedClassname("int[]"));
    assertEquals("int[]", toNormalizedClassname("[I"));

    assertEquals("float[][]", toNormalizedClassname("float[][]"));
    assertEquals("float[][]", toNormalizedClassname("[[F"));

    assertEquals("long[]", toNormalizedClassname("long[]"));
    assertEquals("long[]", toNormalizedClassname("[J"));

    assertEquals("double[][]", toNormalizedClassname("double[][]"));
    assertEquals("double[][]", toNormalizedClassname("[[D"));
  }

  @Test
  void objectReferencesToJavaClassName() {
    assertEquals("java.lang.String", toNormalizedClassname("java.lang.String"));
    assertEquals("java.lang.String", toNormalizedClassname("java/lang/String"));
    assertEquals("java.lang.String", toNormalizedClassname("Ljava/lang/String;"));

    assertEquals("java.lang.String[]", toNormalizedClassname("java.lang.String[]"));
    assertEquals("java.lang.String[]", toNormalizedClassname("[Ljava/lang/String;"));

    assertEquals("java.lang.String[][]", toNormalizedClassname("java.lang.String[][]"));
    assertEquals("java.lang.String[][]", toNormalizedClassname("[[Ljava/lang/String;"));

    assertEquals("java.lang.String[][][]", toNormalizedClassname("java.lang.String[][][]"));
    assertEquals("java.lang.String[][][]", toNormalizedClassname("[[[Ljava/lang/String;"));
  }

  @Override
  public String toNormalizedClassname(final String typeName) {
    return nameProvider.toNormalizedClassname(typeName);
  }
}