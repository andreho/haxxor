package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.Utils;
import net.andreho.haxxor.spi.HxByteCodeLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class DefaultHxByteCodeLoader
    implements HxByteCodeLoader {

  @Override
  @SuppressWarnings("Duplicates")
  public byte[] load(final Haxxor haxxor, final String className) {
    final ClassLoader classLoader = haxxor.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(toFilename(className))) {
      return Utils.toByteArray(inputStream);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Class '" + className + "' not found by the associated class loader: " + classLoader, e);
    }
  }

  private String toFilename(final String className) {
    return className.replace('.', '/') + ".class";
  }
}
