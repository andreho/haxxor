package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectFactory;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 15:22.
 */
public class AbstractFactoryAwareAspectAdvice<T> extends AbstractShadowingAspectAdvice<T> {

  public AbstractFactoryAwareAspectAdvice(final int index,
                                             final AspectAdviceType type,
                                             final ElementMatcher<T> elementMatcher,
                                             final String profileName,
                                             final HxMethod interceptor,
                                             final AspectAdviceParameterInjector parameterInjector,
                                             final AspectAdvicePostProcessor resultHandler) {
    super(index, type, elementMatcher, profileName, interceptor, parameterInjector, resultHandler);
  }

  protected void instantiateAspectInstance(final AspectContext context,
                                           final HxInstruction instruction) {

    final AspectMethodContext methodContext = context.getAspectMethodContext();
    final AspectFactory aspectFactory = context.getAspectDefinition().getAspectFactory().get();
    final HxMethod aspectFactoryMethod = aspectFactory.getMethod();
    final HxInstruction anchor = instruction.getNext();
    final HxExtendedCodeStream stream = instruction.asStream();

    final String aspectAttributeName = aspectFactory.getAspectAttributeName();
    if(methodContext.hasLocalAttribute(aspectAttributeName)) {
      final AspectLocalAttribute localAttribute = methodContext.getLocalAttribute(aspectAttributeName);
      stream.GENERIC_LOAD(localAttribute.getType(), localAttribute.getIndex());
    }
    HxType aspectType;
    if(aspectFactoryMethod.isConstructor()) {
      aspectType = aspectFactoryMethod.getDeclaringMember();
      stream
        .NEW(aspectType)
        .DUP();
    } else {
      aspectType = aspectFactoryMethod.getReturnType();
    }

    injectParameters(context,
                     aspectFactoryMethod,
                     methodContext.getOriginalMethod(),
                     methodContext.getShadowMethod(),
                     anchor.getPrevious());
    stream.INVOKE(aspectFactoryMethod);

    if(aspectFactory.isReusable()) {
      final int slotIndex = methodContext.getNextSlotIndex();
      HxCgenUtils.shiftAccessToLocalVariable(methodContext.getStart(), slotIndex, 1);
      methodContext.createLocalAttribute(aspectType, aspectAttributeName, slotIndex);
      stream.DUP()
            .GENERIC_STORE(aspectType, slotIndex);
    }
  }
}
