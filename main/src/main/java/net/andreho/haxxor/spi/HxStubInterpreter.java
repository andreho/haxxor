package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:28.
 */
@FunctionalInterface
public interface HxStubInterpreter {

  /**
   * @param target
   * @param stub
   */
  void interpret(HxType target, HxType stub);
}
