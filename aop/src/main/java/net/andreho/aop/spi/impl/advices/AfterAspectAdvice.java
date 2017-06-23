package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxMethodBody;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class AfterAspectAdvice
  extends AbstractCallingAspectAdvice<HxMethod> {

  public AfterAspectAdvice(final int index,
                           final AspectAdviceType type,
                           final String profileName,
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

    System.out.println("@After[" + getIndex() + "]: " + original);

    final String shadowMethodName = ctx.getAspectDefinition().formTargetMethodName(original.getName());
    final HxMethod shadow = findOrCreateShadowMethod(original, shadowMethodName);

    final HxMethodBody code = original.getBody();
    final HxMethod interceptor = getInterceptor();
    final boolean injectsResult = interceptor.hasParameterWithAnnotation(Constants.INJECT_RESULT_ANNOTATION_TYPE);

    code.getFirst().forEachNext(
      ins -> ins.hasSort(HxInstructionSort.Exit) && ins.hasNot(HxInstructionsType.Exit.ATHROW),
      ins -> handleReturnOpcode(ctx, injectsResult, interceptor, original, shadow, ins.getPrevious())
    );

    return true;
  }

  private void handleReturnOpcode(final AspectContext ctx,
                                  final boolean injectsResult,
                                  final HxMethod interceptor,
                                  final HxMethod original,
                                  final HxMethod shadow,
                                  final HxInstruction instruction) {

    AspectAttribute resultAttribute;

    final HxInstruction anchor = instruction.getNext();

    if(injectsResult && !original.getReturnType().isVoid()) {
      if(!ctx.hasLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME)) {
        final int slotOffset = original.getParametersSlots() + (original.isStatic() ? 0 : 1);
        final HxType returnType = original.getReturnType();

        resultAttribute =
          ctx.createLocalAttribute(
            Constants.RESULT_ATTRIBUTES_NAME,
            returnType,
            slotOffset
          );

        final int variableSize = returnType.getSlotsCount();

        HxCgenUtils.shiftAccessToLocalVariable(original.getBody().getFirst(), slotOffset, variableSize);
      } else {
        resultAttribute = ctx.getLocalAttribute(Constants.RESULT_ATTRIBUTES_NAME);
      }
      HxExtendedCodeStream stream = anchor.getPrevious().asStream();
      HxCgenUtils.genericDuplicate(resultAttribute.getType(), stream);
      HxCgenUtils.genericStoreSlot(resultAttribute.getType(), resultAttribute.getIndex(), stream);
    }

    handleInterceptorParameters(ctx, interceptor, original, shadow, anchor.getPrevious());

    anchor.getPrevious().asStream().INVOKESTATIC(interceptor);

    handleReturnValue(ctx, interceptor, original, shadow, anchor.getPrevious());
  }

  private void handleReturnValue(final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxInstruction instruction) {

    getResultHandler().handleReturnValue(this, context, interceptor, original, shadow, instruction);
  }

  private void handleInterceptorParameters(final AspectContext ctx,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxInstruction instruction) {

    final AspectAdviceParameterInjector parameterInjector = getParameterInjector();
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
    return "@After(...)";
  }
}
