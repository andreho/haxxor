package net.andreho.haxxor.spi;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.stub.errors.HxStubException;

/**
 * <br/>Created by a.hofmann on 21.10.2017 at 14:28.
 */
@FunctionalInterface
public interface HxStubInjector {
  /**
   * @param target for the given stub class
   * @param stub class
   * @return <b>true</b> if injection was done, <b>false</b> to proceed with execution
   */
  boolean injectStub(HxType target, HxType stub) throws HxStubException;
}
