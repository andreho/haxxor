package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgInjector
  extends AbstractAnnotatedInjector {

  public ArgInjector() {
    super(Arg.class.getName());
  }

  @Override
  protected void checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {
    throw new UnsupportedOperationException();
  }
}
