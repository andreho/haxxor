package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.This;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ThisInjector
  extends AbstractAnnotatedInjector {
  public static final ThisInjector INSTANCE = new ThisInjector();

  public ThisInjector() {
    super(This.class.getName());
  }

  @Override
  protected void checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {
    final HxExtendedCodeStream stream = instruction.asStream();
    if (method.isStatic() ||
        method.isConstructor()) { //don't allow an uninitialized instance to be used somewhere
      stream.ACONST_NULL();
    } else {
      stream.THIS();
    }
  }
}
