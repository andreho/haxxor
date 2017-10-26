package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.List;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class AfterAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  public AfterAspectAdvice(final String profileName,
                           final AspectAdviceType type,
                           final ElementMatcher<HxMethod> elementMatcher,
                           final List<HxMethod> interceptors) {
    super(type,
          elementMatcher,
          profileName,
          interceptors,
          type.getParameterInjectorSelector(),
          type.getResultPostProcessor()
    );
  }

  @Override
  public boolean apply(final AspectContext context,
                       final HxMethod original) {

    if (!matches(original) || !original.hasBody() || getInterceptors().isEmpty()) {
      return false;
    }

    final Optional<ParameterInjectorSelector.Result> bestInterceptor =
      findBestSuitableInterceptor(context, original);

    if(!bestInterceptor.isPresent()) {
      return false;
    }

    final ParameterInjectorSelector.Result result = bestInterceptor.get();

    System.out.println("@After[" + getIndex() + "]: " + original);
    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = result.method;
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    handleReturnOpcode(context, interceptor, original, shadow, methodContext.getDelegationEnd(), result);

    return true;
  }

  private void handleReturnOpcode(final AspectContext context,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction anchor,
                                  final ParameterInjectorSelector.Result result) {

    if (!interceptor.isStatic()) {
      instantiateAspectInstance(context, original, original, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor, result);

    anchor.asAnchoredStream().INVOKE(interceptor);

    postProcessResult(context, interceptor, original, shadow, anchor);
  }

  @Override
  public String toString() {
    return "@After(...)";
  }
}
