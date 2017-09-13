package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.AspectTryCatchImpl;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.Haxxor;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class FinallyAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  private static final AtomicLong COUNTER = new AtomicLong();

  public FinallyAspectAdvice(final int index,
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
    if (!matches(original) || !original.hasBody()) {
      return false;
    }

    final Haxxor haxxor = original.getHaxxor();
    final HxType throwableType = haxxor.resolve("java.lang.Throwable");

    System.out.println("@Finally[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = getInterceptor();
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    AspectTryCatch tryCatch;

    if (!methodContext.hasTryCatchBlock(null)) {
      final LABEL tryBegin = methodContext.getDelegationBegin();
      final LABEL tryEnd = methodContext.getDelegationEnd();
      final LABEL finallyBegin = new LABEL();
      final LABEL finallyEnd = new LABEL();

      final String exceptionVariableName =
        Constants.DEFAULT_CAUGHT_EXCEPTION_ATTRIBUTE_NAME_PREFIX + COUNTER.getAndIncrement();

      final int slotOffset = methodContext.getNextSlotIndex();

      AspectLocalAttribute exceptionAttribute =
        methodContext.createLocalAttribute(throwableType, exceptionVariableName, slotOffset);
      exceptionAttribute
        .getHxLocalVariable()
        .setStart(finallyBegin)
        .setEnd(finallyEnd);

      HxCgenUtils.shiftAccessToLocalVariable(
        original.getBody().getFirst(), slotOffset, throwableType.getSlotSize());

      HxExtendedCodeStream stream = methodContext.getCatchInjection().asAnchoredStream();

      stream
        .LABEL(finallyBegin)
        .ASTORE(slotOffset)
        .LABEL(finallyEnd);

      tryCatch =
        new AspectTryCatchImpl(
          null, tryBegin, tryEnd, finallyBegin, finallyEnd, exceptionAttribute.getHxLocalVariable()
        );

      methodContext.createTryCatchBlock(tryCatch);

      original
        .getBody()
        .addTryCatch(tryCatch.getHxTryCatch());
    } else {
      tryCatch = methodContext.getTryCatchBlock(null);
    }

    invokeCatchAspect(context, tryCatch, interceptor, original, shadow, tryCatch.getCatchEnd());
    return true;
  }

  private void invokeCatchAspect(final AspectContext context,
                                 final AspectTryCatch tryCatch,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxInstruction anchor) {

    if (needsAspectFactory()) {
      instantiateAspectInstance(context, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor);

    anchor.asAnchoredStream().INVOKE(interceptor);

    postProcessResult(context, interceptor, original, shadow, anchor);
  }

  @Override
  public String toString() {
    return "@Finally()";
  }
}
