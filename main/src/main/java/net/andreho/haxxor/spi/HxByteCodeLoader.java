package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public interface HxByteCodeLoader {

  /**
   * @param classLoader of a class being attempted for loading
   * @param className of a class being attempted for loading
   * @return content of the requested class as a byte-array
   */
  byte[] load(final ClassLoader classLoader, final String className);
}
