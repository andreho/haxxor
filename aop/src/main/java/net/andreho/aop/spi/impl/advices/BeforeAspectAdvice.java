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
public class BeforeAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  public BeforeAspectAdvice(final String profileName,
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

    System.out.println("@Before[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = result.method;
    final AspectMethodContext methodContext = context.getAspectMethodContext();
    final HxInstruction anchor = methodContext.getDelegationBegin();

    if(!result.method.isStatic()) {
      instantiateAspectInstance(context, original, result.method, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor, result);

    anchor.asAnchoredStream().INVOKE(interceptor);

    postProcessResult(context, interceptor, original, shadow, anchor);

//    final HxMethodBody body = addNewLocalAttributes(original, methodContext);
//    System.out.println(body);

    return true;
  }

  @Override
  public String toString() {
    return "@Before(...)";
  }
}
