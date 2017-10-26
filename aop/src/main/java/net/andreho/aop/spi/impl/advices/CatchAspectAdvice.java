package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.impl.AspectTryCatchImpl;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class CatchAspectAdvice
  extends AbstractFactoryAwareAspectAdvice<HxMethod> {

  private static final AtomicLong COUNTER = new AtomicLong();
  private final HxType exception;

  public CatchAspectAdvice(final String profileName,
                           final AspectAdviceType type,
                           final ElementMatcher<HxMethod> elementMatcher,
                           final List<HxMethod> interceptors,
                           final HxType exception) {
    super(type,
          elementMatcher,
          profileName,
          interceptors,
          type.getParameterInjectorSelector(),
          type.getResultPostProcessor()
    );
    this.exception = exception;
  }

  @Override
  public boolean apply(final AspectContext context,
                       final HxMethod original) {
    if (!matches(original) || !original.hasBody() || !getInterceptors().isEmpty()) {
      return false;
    }

    final Optional<ParameterInjectorSelector.Result> bestInterceptor =
      findBestSuitableInterceptor(context, original);

    if(!bestInterceptor.isPresent()) {
      return false;
    }

    final ParameterInjectorSelector.Result result = bestInterceptor.get();

    System.out.println("@Catch[" + getIndex() + "]: " + original);

    final HxMethod shadow = findOrCreateShadowMethod(
      context, original, context.getAspectDefinition().createShadowMethodName(original.getName()));

    final HxMethod interceptor = result.method;
    final AspectMethodContext methodContext = context.getAspectMethodContext();

    AspectTryCatch tryCatch;

    if (!methodContext.hasTryCatchBlock(exception.getName())) {
      final LABEL tryBegin = methodContext.getDelegationBegin();
      final LABEL tryEnd = methodContext.getDelegationEnd();
      final LABEL catchBegin = new LABEL();
      final LABEL catchEnd = new LABEL();

      final String exceptionVariableName =
        Constants.DEFAULT_CAUGHT_EXCEPTION_ATTRIBUTE_NAME_PREFIX + COUNTER.getAndIncrement();

      final int slotOffset = methodContext.getNextSlotIndex();

      AspectLocalAttribute exceptionAttribute =
        methodContext.createLocalAttribute(exception, exceptionVariableName, slotOffset);
      exceptionAttribute
        .getHxLocalVariable()
        .setStart(catchBegin)
        .setEnd(catchEnd);

      HxCgenUtils.shiftAccessToLocalVariable(
        original.getBody().getFirst(), slotOffset, exception.getSlotSize());

      HxExtendedCodeStream stream = methodContext.getCatchInjection().asAnchoredStream();

      stream
        .LABEL(catchBegin)
        .ASTORE(slotOffset)
        .LABEL(catchEnd);

      tryCatch =
        new AspectTryCatchImpl(
          exception, tryBegin, tryEnd, catchBegin, catchEnd, exceptionAttribute.getHxLocalVariable()
        );

      methodContext.createTryCatchBlock(tryCatch);

      original
        .getBody()
        .addTryCatch(tryCatch.getHxTryCatch());
    } else {
      tryCatch = methodContext.getTryCatchBlock(exception.getName());
    }

    invokeCatchAspect(context, tryCatch, interceptor, original, shadow, tryCatch.getCatchEnd(), result);
    return true;
  }

  private void invokeCatchAspect(final AspectContext context,
                                 final AspectTryCatch tryCatch,
                                 final HxMethod interceptor,
                                 final HxMethod original,
                                 final HxMethod shadow,
                                 final HxInstruction anchor,
                                 final ParameterInjectorSelector.Result result) {

    if (needsAspectFactory()) {
      instantiateAspectInstance(context, original, original, anchor);
    }

    injectParameters(context, interceptor, original, shadow, anchor, result);

    anchor.asAnchoredStream().INVOKE(interceptor);

    postProcessResult(context, interceptor, original, shadow, anchor);
  }

  @Override
  public String toString() {
    return "@Catch(\""+exception+"\")";
  }
}
