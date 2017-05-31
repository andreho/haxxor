package net.andreho.haxxor.spi;

import net.andreho.haxxor.Haxxor;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public interface HxByteCodeLoader {

  /**
   * @param haxxor    is the requesting haxxor instance
   * @param className of a class being loaded
   * @return content of the requested class as a bytearray
   */
  byte[] load(Haxxor haxxor, String className);
}
