package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.HaxxorBuilder;
import net.andreho.haxxor.model.MinimalBean;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static net.andreho.haxxor.Utils.toByteArray;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>Created by a.hofmann on 02.06.2017 at 14:36.
 */
class DefaultHxByteCodeLoaderTest
    implements HxByteCodeLoader {

  private HxByteCodeLoader byteCodeLoader;
  private Haxxor haxxor;

  @BeforeEach
  void setup() {
    this.haxxor = new Haxxor(0, new HaxxorBuilder() {
      @Override
      public HxByteCodeLoader createByteCodeLoader(final Haxxor haxxor) {
        return byteCodeLoader = new DefaultHxByteCodeLoader(haxxor);
      }
    });
  }

  @Override
  public byte[] load(final String className) {
    return byteCodeLoader.load(className);
  }

  @Test
  void testLoadByteCode()
  throws IOException {
    byte[] loaded = load(MinimalBean.class.getName());
    byte[] original = null;

    try (InputStream inputStream = haxxor.getClassLoader().getResourceAsStream(
        MinimalBean.class.getName()
                         .replace('.', '/') + ".class")
    ) {
      assertNotNull(inputStream);
      original = toByteArray(inputStream);
      assertNotNull(original);
    }

    assertTrue(Arrays.equals(original, loaded));
  }
}