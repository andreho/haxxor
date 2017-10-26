package net.andreho.aop.spi.impl;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 07:15.
 */
public class AspectContextImpl
  implements AspectContext {

  private volatile HxParameter parameter;
  private volatile HxMethod constructor;
  private volatile HxMethod method;
  private volatile HxField field;
  private volatile HxType type;

  private volatile AspectAdvice<?> aspectAdvice;
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
  public void leaveAspect(final AspectAdvice<?> aspectAdvice) {
    this.aspectAdvice = null;
  }

  @Override
  public void leaveType(final HxType type) {
    this.type = null;
  }

  @Override
  public void leaveField(final HxField field) {
    this.field = null;
  }

  @Override
  public void leaveMethod(final HxMethod method) {
    this.method = null;
  }

  @Override
  public void leaveConstructor(final HxMethod constructor) {
    this.constructor = null;
  }

  @Override
  public void enterParameter(final HxParameter parameter) {
    this.parameter = parameter;
  }

  @Override
  public void leaveParameter(final HxParameter parameter) {
    this.parameter = null;
  }

  @Override
  public void enterAspect(final AspectAdvice<?> aspectAdvice) {
    this.aspectAdvice = aspectAdvice;
  }

  @Override
  public void enterType(final HxType type) {
    this.type = type;
  }

  @Override
  public void enterField(final HxField field) {
    this.field = field;
  }

  @Override
  public void enterMethod(final HxMethod method) {
    getAspectMethodContext().reset();
    this.method = method;
  }

  @Override
  public void enterConstructor(final HxMethod constructor) {
    getAspectMethodContext().reset();
    this.constructor = constructor;
  }

  @Override
  public AspectMethodContext getAspectMethodContext() {
    return aspectMethodContext;
  }

  @Override
  public AspectDefinition getAspectDefinition() {
    return aspectDefinition;
  }

  @Override
  public AspectAdvice<?> getAspectAdvice() {
    return aspectAdvice;
  }
}
