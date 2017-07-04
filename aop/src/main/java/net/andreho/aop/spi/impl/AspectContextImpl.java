package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.haxxor.spec.api.HxField;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 07:15.
 */
public class AspectContextImpl
  implements AspectContext {

  private final AspectDefinition aspectDefinition;
  private final AspectMethodContext aspectMethodContext;

  public AspectContextImpl(final AspectDefinition aspectDefinition) {
    this(aspectDefinition, new AspectMethodContextImpl());
  }

  public AspectContextImpl(final AspectDefinition aspectDefinition, final AspectMethodContext aspectMethodContext) {
    this.aspectDefinition = aspectDefinition;
    this.aspectMethodContext = aspectMethodContext;
  }

  @Override
  public void enterType(final HxType type) {

  }

  @Override
  public void enterField(final HxField field) {

  }

  @Override
  public void enterMethod(final HxMethod method) {
    getAspectMethodContext().reset();
  }

  @Override
  public void enterConstructor(final HxMethod constructor) {
    getAspectMethodContext().reset();
  }

  @Override
  public AspectMethodContext getAspectMethodContext() {
    return aspectMethodContext;
  }

  @Override
  public AspectDefinition getAspectDefinition() {
    return aspectDefinition;
  }
}
