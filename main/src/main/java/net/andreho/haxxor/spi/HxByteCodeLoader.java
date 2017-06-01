package net.andreho.haxxor.spi;

/**
 * <br/>Created by a.hofmann on 16.03.2016.<br/>
 */
public interface HxByteCodeLoader {

  /**
   * @param className of a class being loaded
   * @return content of the requested class as a bytearray
   */
  byte[] load(String className);
}
