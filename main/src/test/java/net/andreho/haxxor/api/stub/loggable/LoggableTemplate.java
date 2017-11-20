package net.andreho.haxxor.api.stub.loggable;

import net.andreho.haxxor.api.stub.Stub;

import java.util.logging.Logger;

/**
 * <br/>Created by a.hofmann on 18.11.2017 at 15:51.
 */
public class LoggableTemplate implements Loggable {
  @Stub.DeclaringClass static Class<?> __class__() { return LoggableTemplate.class; }
  //------------------------------------------------------------------------------------------------------------------
  private static final Logger LOG = Logger.getLogger(__class__().getName());

  @Override
  public Logger log() {
    return LOG;
  }
}
