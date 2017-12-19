package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.Hx;
import net.andreho.haxxor.spi.HxByteCodeLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import static net.andreho.haxxor.utils.CommonUtils.toByteArray;

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
  public Optional<byte[]> load(final ClassLoader classloader, final String classname) {
    try (InputStream inputStream = classloader.getResourceAsStream(toFilename(classname))) {
      if(inputStream == null) {
        return Optional.empty();
      }
      return Optional.of(toByteArray(inputStream));
    } catch (IOException e) {
      throw new IllegalArgumentException(
        "Class '" + classname + "' was not found by the associated class loader: " + classloader, e);
    }
  }

  private String toFilename(final String className) {
    return className.replace('.', '/') + ".class";
  }
}
