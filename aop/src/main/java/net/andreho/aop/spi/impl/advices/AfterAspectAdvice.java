package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxSort;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class AfterAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  public AfterAspectAdvice(final int index,
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

    System.out.println("@After[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = getInterceptor();
    final AspectMethodContext methodContext = context.getAspectMethodContext();

//    methodContext.getDelegationEnd().forPrevious(
//      ins -> ins.hasSort(HxInstructionSort.Exit) && ins.hasNot(HxInstructionsType.Exit.ATHROW),
//      ins -> handleReturnOpcode(context, interceptor, original, shadow, ins)
//    );

    handleReturnOpcode(context, interceptor, original, shadow, methodContext.getDelegationEnd());

    return true;
  }

  private void handleReturnOpcode(final AspectContext context,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction anchor) {

    if(interceptor.hasParameterWithAnnotation(Constants.INJECT_RESULT_ANNOTATION_TYPE)) {
      storeResultOfShadow(context, original, anchor);
    }

    if(needsAspectFactory()) {
      instantiateAspectInstance(context, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor);

    anchor.asAnchoredStream().INVOKE(interceptor);

    postProcessResult(context, interceptor, original, shadow, anchor);

//    addNewLocalAttributes(original, context.getAspectMethodContext());

//    System.out.println(original.getBody());
  }

  private void storeResultOfShadow(final AspectContext context,
                                   final HxMethod original,
                                   final HxInstruction anchor) {

    if(!original.hasReturnType(HxSort.VOID)) {
      AspectLocalAttribute resultAttribute;
      final AspectMethodContext methodContext = context.getAspectMethodContext();

      if(!methodContext.hasLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME)) {
        //original.getParametersSlotSize() + (original.isStatic() ? 0 : 1);
        final int slotOffset = methodContext.getNextSlotIndex();
        final HxType returnType = original.getReturnType();

        resultAttribute =
          methodContext.createLocalAttribute(returnType, Constants.RESULT_ATTRIBUTES_NAME, slotOffset);
        final int variableSize = returnType.getSlotSize();

        HxCgenUtils.shiftAccessToLocalVariable(original.getBody().getFirst(), slotOffset, variableSize);
      } else {
        resultAttribute = methodContext.getLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME);
      }

      HxExtendedCodeStream stream = anchor.asAnchoredStream();
      stream
        .GENERIC_DUP(resultAttribute.getType())
        .GENERIC_STORE(resultAttribute.getType(), resultAttribute.getIndex());
    }
  }

  @Override
  public String toString() {
    return "@After(...)";
  }
}
