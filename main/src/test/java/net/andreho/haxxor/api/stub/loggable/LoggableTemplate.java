package net.andreho.haxxor.api.stub.loggable;

import net.andreho.haxxor.api.stub.Stub;

import java.util.logging.Logger;

public class LoggableTemplate implements Loggable {
  @Stub.Class static Class __class__() { return LoggableTemplate.class; }
  private static final Logger LOG = Logger.getLogger(__class__().getName());

  @Override
  public Logger log() {
    return LOG;
  }
}
