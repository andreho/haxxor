package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxStubInjector;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 16:19.
 */
public class ServiceStubInjector
  extends CompoundHxStubInjector {
  private static final HxStubInjector[] HANDLERS;

  static {
    final ServiceLoader<HxStubInjector> serviceLoader = ServiceLoader.load(HxStubInjector.class);
    final List<HxStubInjector> list = new ArrayList<>();
    for(HxStubInjector handler : serviceLoader) {
      list.add(handler);
    }
    HANDLERS = list.toArray(new HxStubInjector[0]);
  }

  public ServiceStubInjector() {
    super(false, HANDLERS);
  }
}
