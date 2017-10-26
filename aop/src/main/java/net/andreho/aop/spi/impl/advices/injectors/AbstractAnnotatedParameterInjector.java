package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

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
                                       final HxInstruction anchor) {
    if(!isInjectable(context, interceptor, original, shadow, parameter)) {
      return false;
    }

    return checkedParameterInjection(aspectAdvice, context, interceptor, original, shadow, parameter, anchor);
  }

  protected abstract boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                                    final AspectContext context,
                                                    final HxMethod interceptor,
                                                    final HxMethod original,
                                                    final HxMethod shadow,
                                                    final HxParameter parameter,
                                                    final HxInstruction anchor);
}
