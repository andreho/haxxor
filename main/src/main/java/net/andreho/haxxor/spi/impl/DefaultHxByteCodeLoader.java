package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.Utils;
import net.andreho.haxxor.spi.HxByteCodeLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class DefaultHxByteCodeLoader
    implements HxByteCodeLoader {

  private final Haxxor haxxor;

  public DefaultHxByteCodeLoader(final Haxxor haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor, "Haxxor instance can't be null.");
  }

  public Haxxor getHaxxor() {
    return haxxor;
  }

  @Override
  @SuppressWarnings("Duplicates")
  public byte[] load(final String className) {
    final ClassLoader classLoader = haxxor.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(toFilename(className))) {
      return Utils.toByteArray(inputStream);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Class '" + className + "' was not found by the associated class loader: " + classLoader, e);
    }
  }

  private String toFilename(final String className) {
    return className.replace('.', '/') + ".class";
  }
}
