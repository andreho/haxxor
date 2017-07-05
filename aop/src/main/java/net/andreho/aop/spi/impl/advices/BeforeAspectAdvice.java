package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class BeforeAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  public BeforeAspectAdvice(final int index,
                            final String profileName,
                            final AspectAdviceType type,
                            final ElementMatcher<HxMethod> elementMatcher,
                            final HxMethod interceptor) {
    super(index,
          type,
          elementMatcher,
          profileName,
          interceptor,
          type.getParameterInjector(),
          type.getPostProcessor()
    );
  }

  @Override
  public boolean apply(final AspectContext context,
                       final HxMethod original) {
    if (!match(original) || !original.hasBody()) {
      return false;
    }

    System.out.println("@Before[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = getInterceptor();
    final AspectMethodContext methodContext = context.getAspectMethodContext();
    final HxInstruction anchor = methodContext.getDelegationStart();

    if(needsAspectFactory()) {
      instantiateAspectInstance(context, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor);

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
