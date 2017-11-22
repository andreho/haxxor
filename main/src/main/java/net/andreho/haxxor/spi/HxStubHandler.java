package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:28.
 */
@FunctionalInterface
public interface HxStubHandler {

  /**
   * @param target for the given stub class
   * @param stub class
   */
  void handle(HxType target, HxType stub) throws HxStubException;
}
