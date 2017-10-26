package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectFactory;
import net.andreho.haxxor.api.HxMethod;

import static java.util.Objects.requireNonNull;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 16:28.
 */
public class AspectFactoryImpl
  implements AspectFactory {

  private final HxMethod factoryMethod;
  private final String aspectAttributeName;
  private final boolean reusable;

  public AspectFactoryImpl(final HxMethod factoryMethod,
                           final String aspectAttributeName,
                           final boolean reusable) {
    this.factoryMethod = requireNonNull(factoryMethod);
    this.aspectAttributeName = requireNonNull(aspectAttributeName);
    this.reusable = reusable;
  }

  @Override
  public boolean isReusable() {
    return reusable;
  }

  @Override
  public HxMethod getMethod() {
    return factoryMethod;
  }

  @Override
  public String getAspectAttributeName() {
    return aspectAttributeName;
  }
}
