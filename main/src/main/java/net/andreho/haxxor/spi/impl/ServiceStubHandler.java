package net.andreho.haxxor.spi.impl;

import net.andreho.haxxor.spi.HxStubHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * <br/>Created by a.hofmann on 21.11.2017 at 16:19.
 */
public class ServiceStubHandler
  extends CompoundHxStubHandler {
  private static final HxStubHandler[] HANDLERS = (new Supplier<HxStubHandler[]>() {
    @Override
    public HxStubHandler[] get() {
      final ServiceLoader<HxStubHandler> serviceLoader = ServiceLoader.load(HxStubHandler.class);
      final List<HxStubHandler> list = new ArrayList<>();
      for(HxStubHandler handler : serviceLoader) {
        list.add(handler);
      }
      return list.toArray(new HxStubHandler[0]);
    }
  }).get();

  public ServiceStubHandler() {
    super(false, HANDLERS);
  }
}
