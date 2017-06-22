package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgInjector
  extends AbstractAnnotatedInjector {

  public ArgInjector() {
    super(Arg.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {

    for (int i = 0, arity = original.getParametersCount(); i < arity; i++) {
      final HxType parameterTypeAt = original.getParameterTypeAt(i);
      if(parameterTypeAt.equals(parameter.getType())) {
        HxExtendedCodeStream stream = instruction.asStream();
        HxCgenUtils.genericLoadDefault(parameterTypeAt, stream);
      }
    }
    throw new UnsupportedOperationException();
  }
}
