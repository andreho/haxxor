package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.spi.HxByteCodeLoader;
import net.andreho.haxxor.utils.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public class DefaultHxByteCodeLoader
    implements HxByteCodeLoader {

  private final Hx haxxor;

  public DefaultHxByteCodeLoader(final Hx haxxor) {
    this.haxxor = Objects.requireNonNull(haxxor, "Haxxor instance can't be null.");
  }

  public Hx getHaxxor() {
    return haxxor;
  }

  @Override
  @SuppressWarnings("Duplicates")
  public byte[] load(final ClassLoader classLoader, final String className) {
    try (InputStream inputStream = classLoader.getResourceAsStream(toFilename(className))) {
      if(inputStream == null) {
        throw new IllegalArgumentException("Class wasn't found: "+className);
      }
      return CommonUtils.toByteArray(inputStream);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Class '" + className + "' was not found by the associated class loader: " + classLoader, e);
    }
  }

  private String toFilename(final String className) {
    return className.replace('.', '/') + ".class";
  }
}
