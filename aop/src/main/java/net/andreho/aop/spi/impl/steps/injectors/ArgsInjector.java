package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgsInjector
  extends AbstractAnnotatedInjector {
  public static final ArgsInjector INSTANCE = new ArgsInjector();

  public ArgsInjector() {
    super(Args.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {

    HxCgenUtils.packArguments(original.getParameterTypes(), original.isStatic() ? 0 : 1, instruction.asStream());
    return true;
  }
}
