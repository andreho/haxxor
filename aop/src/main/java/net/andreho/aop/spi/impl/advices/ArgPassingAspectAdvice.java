package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class ArgPassingAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxParameter> {

  public ArgPassingAspectAdvice(final int index,
                                final String profileName,
                                final AspectAdviceType type,
                                final ElementMatcher<HxParameter> elementMatcher,
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
                       final HxParameter parameter) {
    final HxMethod original = parameter.getDeclaringMember();
    if (!matches(parameter) || !original.hasBody()) {
      return false;
    }

    System.out.println("@ArgPassing[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

//    final HxMethod interceptor = getInterceptor();
//    final AspectMethodContext methodContext = context.getAspectMethodContext();
//    final HxInstruction anchor = methodContext.getDelegationBegin();
//
//    if(needsAspectFactory()) {
//      instantiateAspectInstance(context, original, original, anchor);
//    }
//
//    injectParameters(context, interceptor, original, shadow, anchor);
//
//    anchor.asAnchoredStream().INVOKE(interceptor);
//
//    postProcessResult(context, interceptor, original, shadow, anchor);

    return true;
  }
//    final HxMethodBody body = addNewLocalAttributes(original, methodContext);
//    System.out.println(body);

  @Override
  public String toString() {
    return "@Before(...)";
  }
}
