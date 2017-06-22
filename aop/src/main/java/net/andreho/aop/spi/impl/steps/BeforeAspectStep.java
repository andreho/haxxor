package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxParameter;

import java.util.function.Supplier;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class BeforeAspectStep
  extends AbstractCallingAspectStep<HxMethod> {

  public BeforeAspectStep(final int index,
                          final String profileName,
                          final AspectStepType type,
                          final ElementMatcher<HxMethod> elementMatcher,
                          final HxMethod interceptor) {
    super(index,
          type,
          elementMatcher,
          profileName,
          interceptor,
          type.getParameterInjector(),
          type.getResultHandler()
    );
  }

  @Override
  public boolean apply(final AspectContext ctx,
                       final HxMethod original) {
    if (!match(original) || !original.hasBody()) {
      return false;
    }

    System.out.println("@Before[" + getIndex() + "]: " + original);

    final String shadowMethodName = ctx.getAspectDefinition().formTargetMethodName(original.getName());
    final HxMethod shadow = findOrCreateShadowMethod(original, shadowMethodName);

    final HxMethodBody code = original.getBody();
    final HxMethod interceptor = getInterceptor();

    final HxInstruction begin = code.getFirst();
    final HxInstruction anchor = begin.getNext();

    final AspectDefinition aspectDefinition = ctx.getAspectDefinition();
    final boolean needsAspectFactory = needsAspectFactory();

//    if (needsAspectFactory) {
//      final String aspectFactoryAttributesName = formAspectFactoryAttributesName(aspectDefinition);
//
//      if (aspectDefinition.isFactoryReusable()) {
//        if(ctx.hasLocalAttribute(aspectFactoryAttributesName)) {
//          final AspectAttribute attribute = ctx.getLocalAttribute(aspectFactoryAttributesName);
//
//        }
//      }
//
//      final HxMethod aspectFactory = aspectDefinition
//        .getAspectFactory().orElseThrow(complainAboutMissingAspectFactory(ctx));
//      handleInterceptorParameters(ctx, aspectFactory, original, anchor.getPrevious());
//      anchor.getPrevious().asStream().INVOKESTATIC(aspectFactory);
//
//      if (aspectDefinition.isFactoryReusable()) {
//        ctx.hasLocalAttribute(aspectFactoryAttributesName);
//      } else {
//
//      }
////      final HxAnnotation aspectFactoryAnnotation = aspectFactory
////        .getAnnotation(Constants.ASPECT_FACTORY_ANNOTATION_TYPE).get();
//    }

    handleInterceptorParameters(ctx, interceptor, original, shadow, anchor.getPrevious());

    if (!needsAspectFactory) {
      anchor.getPrevious().asStream().INVOKESTATIC(interceptor);
    } else {
      anchor.getPrevious().asStream().INVOKEVIRTUAL(interceptor);
    }
    handleReturnValue(ctx, interceptor, original, shadow, anchor.getPrevious());

    return true;
  }

  private Supplier<IllegalStateException> complainAboutMissingAspectFactory(final AspectContext ctx) {
    return () ->
      new IllegalStateException(
        "Current aspect expects to have a public static method that returns a " +
        "valid instance of aspect's type and must be marked with @Aspect.Factory: " +
        ctx.getAspectDefinition().getType());
  }

  private void handleReturnValue(final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxInstruction instruction) {

    getResultHandler().handleReturnValue(this, context, interceptor, shadow, shadow, instruction);
  }

  private void handleInterceptorParameters(final AspectContext ctx,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxInstruction instruction) {

    final AspectStepParameterInjector parameterInjector = getParameterInjector();
    final HxInstruction anchor = instruction.getNext();

    for (HxParameter parameter : interceptor.getParameters()) {
      if (!parameterInjector.injectParameter(this, ctx, interceptor, original, shadow, parameter, anchor.getPrevious())) {
        throw new IllegalStateException(
          "Unable to inject parameter of " + interceptor + " at position: " + parameter.getIndex());
      }
    }
  }

  @Override
  public String toString() {
    return "@Before(...)";
  }

  private static String formAspectFactoryAttributesName(final AspectDefinition aspectDefinition) {
    return "@Aspect.Factory(" + aspectDefinition.getName() + ")";
  }
}
