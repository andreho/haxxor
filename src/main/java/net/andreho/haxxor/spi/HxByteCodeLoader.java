package net.andreho.haxxor.spi;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public interface HxByteCodeLoader {

  /**
   * @param classloader of a class being attempted for loading
   * @param classname of a class being attempted for loading
   * @return content of the requested class as a byte-array
   */
  Optional<byte[]> load(final ClassLoader classloader, final String classname);
}
