package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 03:03.
 */
public abstract class AbstractAnnotatedInjector
  extends AbstractInjector {

  private final String annotationTypename;

  public AbstractAnnotatedInjector(final String annotationTypename) {
    this.annotationTypename = Objects.requireNonNull(annotationTypename);
  }

  @Override
  public final boolean isInjectable(final AspectContext context,
                              final HxMethod interceptor,
                              final HxMethod method,
                              final HxParameter parameter) {
    return parameter.isAnnotationPresent(annotationTypename);
  }

  @Override
  public final boolean injectParameter(final AspectStep<?> aspectStep,
                                 final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod method,
                                 final HxParameter parameter,
                                 final HxInstruction instruction) {
    if(!isInjectable(context, interceptor, method, parameter)) {
      return false;
    }

    checkedParameterInjection(aspectStep, context, interceptor, method, parameter, instruction);
    return true;
  }

  protected abstract void checkedParameterInjection(final AspectStep<?> aspectStep,
                                                    final AspectContext context,
                                                    final HxMethod interceptor,
                                                    final HxMethod method,
                                                    final HxParameter parameter,
                                                    final HxInstruction instruction);
}
