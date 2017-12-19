package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxStubInjector;
import net.andreho.haxxor.stub.errors.HxStubException;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 16:28.
 */
public class CompoundHxStubInjector
  implements HxStubInjector {

  private final HxStubInjector[] handlers;

  public CompoundHxStubInjector(final HxStubInjector... handlers) {
    this(true, handlers);
  }

  protected CompoundHxStubInjector(final boolean copy,
                                   final HxStubInjector... handlers) {
    this.handlers = copy ? handlers.clone() : handlers;
  }

  @Override
  public boolean injectStub(final HxType target,
                            final HxType stub)
  throws HxStubException {
    for (HxStubInjector handler : handlers) {
      try {
        if (handler.injectStub(target, stub)) {
          return true;
        }
      } catch (Exception e) {
        if (e instanceof HxStubException) {
          throw e;
        }
        throw new HxStubException(e);
      }
    }
    return false;
  }
}
