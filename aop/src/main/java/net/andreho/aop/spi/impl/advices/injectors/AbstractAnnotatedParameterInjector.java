package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.Objects;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 03:03.
 */
public abstract class AbstractAnnotatedParameterInjector
  extends AbstractParameterInjector {

  private final String annotationTypename;

  public AbstractAnnotatedParameterInjector(final String annotationTypename) {
    this.annotationTypename = Objects.requireNonNull(annotationTypename);
  }

  @Override
  public final boolean isInjectable(final AspectContext context,
                                    final HxMethod interceptor,
                                    final HxMethod original,
                                    final HxMethod shadow,
                                    final HxParameter parameter) {
    return parameter.isAnnotationPresent(annotationTypename);
  }

  @Override
  public final boolean injectParameter(final AspectAdvice<?> aspectAdvice,
                                       final AspectContext context,
                                       final HxMethod interceptor,
                                       final HxMethod original,
                                       final HxMethod shadow,
                                       final HxParameter parameter,
                                       final HxInstruction instruction) {
    if(!isInjectable(context, interceptor, original, shadow, parameter)) {
      return false;
    }

    return checkedParameterInjection(aspectAdvice, context, interceptor, original, shadow, parameter, instruction);
  }

  protected abstract boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                                    final AspectContext context,
                                                    final HxMethod interceptor,
                                                    final HxMethod original,
                                                    final HxMethod shadow,
                                                    final HxParameter parameter,
                                                    final HxInstruction instruction);
}
