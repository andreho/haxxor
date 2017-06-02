package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxInternalClassNameProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 14:19.
 */
class DefaultHxInternalClassNameProviderTest implements HxInternalClassNameProvider {

  private HxInternalClassNameProvider nameProvider;

  @BeforeEach
  void prepare() {
    nameProvider = new DefaultHxInternalClassNameProvider();
  }

  @Test
  void primitiveTypesToInternalClassName() {
    assertEquals("V", toInternalClassName("void"));
    assertEquals("V", toInternalClassName("V"));

    assertEquals("Z", toInternalClassName("boolean"));
    assertEquals("Z", toInternalClassName("Z"));

    assertEquals("B", toInternalClassName("byte"));
    assertEquals("B", toInternalClassName("B"));

    assertEquals("C", toInternalClassName("char"));
    assertEquals("C", toInternalClassName("C"));

    assertEquals("S", toInternalClassName("short"));
    assertEquals("S", toInternalClassName("S"));

    assertEquals("I", toInternalClassName("int"));
    assertEquals("I", toInternalClassName("I"));

    assertEquals("F", toInternalClassName("float"));
    assertEquals("F", toInternalClassName("F"));

    assertEquals("J", toInternalClassName("long"));
    assertEquals("J", toInternalClassName("J"));

    assertEquals("D", toInternalClassName("double"));
    assertEquals("D", toInternalClassName("D"));
  }

  @Test
  void objectReferencesToInternalClassName() {
    assertEquals("java/lang/String", toInternalClassName("java.lang.String"));
    assertEquals("java/lang/String", toInternalClassName("java/lang/String"));
    assertEquals("java/lang/String", toInternalClassName("Ljava/lang/String;"));

    assertEquals("java/util/Map$Entry", toInternalClassName("java.util.Map$Entry"));
    assertEquals("java/util/Map$Entry", toInternalClassName("java/util/Map$Entry"));
    assertEquals("java/util/Map$Entry", toInternalClassName("Ljava/util/Map$Entry;"));
  }

  @Override
  public String toInternalClassName(final String typeName) {
    return nameProvider.toInternalClassName(typeName);
  }
}