package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArityInjector
  extends AbstractAnnotatedInjector {
  public static final ArityInjector INSTANCE = new ArityInjector();

  public ArityInjector() {
    super(Arity.class.getName());
  }

  @Override
  protected void checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {

    instruction.asStream().LDC(method.getParametersCount());
  }
}
