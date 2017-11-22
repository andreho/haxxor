package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.spi.HxStubException;
import net.andreho.haxxor.spi.HxStubHandler;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 16:28.
 */
public class CompoundHxStubHandler implements HxStubHandler {
  private final HxStubHandler[] handlers;

  public CompoundHxStubHandler(final HxStubHandler ... handlers) {
    this(true, handlers);
  }

  protected CompoundHxStubHandler(final boolean copy, final HxStubHandler ... handlers) {
    this.handlers = copy? handlers.clone() : handlers;
  }

  @Override
  public void handle(final HxType target,
                     final HxType stub)
  throws HxStubException {
    for(HxStubHandler handler : handlers) {
      try {
        handler.handle(target, stub);
      } catch (Exception e) {
        if(e instanceof HxStubException) {
          throw e;
        }
        e.printStackTrace();
        break;
      }
    }
  }
}
