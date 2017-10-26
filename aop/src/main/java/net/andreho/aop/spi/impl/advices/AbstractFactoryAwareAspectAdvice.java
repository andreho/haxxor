package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectFactory;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxOrdered;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

import java.util.List;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 04.07.2017 at 15:22.
 */
public class AbstractFactoryAwareAspectAdvice<T>
  extends AbstractShadowingAspectAdvice<T> {

  protected static <E extends HxAnnotated<E> & HxOrdered>
  Integer getIndex(final E element) {
    return element.getAnnotation(Constants.ORDER_ANNOTATION_TYPE)
                  .map(a -> a.getAttribute("value", Integer.MAX_VALUE))
                  .orElse(Constants.UNORDERED_ELEMENT_INDEX + element.getIndex());
  }

  public AbstractFactoryAwareAspectAdvice(final AspectAdviceType type,
                                          final ElementMatcher<T> elementMatcher,
                                          final String profileName,
                                          final List<HxMethod> interceptors,
                                          final ParameterInjectorSelector parameterInjectorSelector,
                                          final ResultPostProcessor resultHandler) {
    super(type, elementMatcher, profileName, interceptors, parameterInjectorSelector, resultHandler);
  }

  protected Optional<ParameterInjectorSelector.Result> findBestSuitableInterceptor(final AspectContext context,
                                                                                   final HxMethod original) {
    final ParameterInjectorSelector parameterInjectorSelector = getParameterInjectorSelector();
    return parameterInjectorSelector.selectBestSuitableInjectors(context, original, getInterceptors());
  }

  protected boolean instantiateAspectInstance(final AspectContext context,
                                              final HxMethod original,
                                              final HxMethod interceptor,
                                              final HxInstruction anchor) {

    final AspectMethodContext methodContext = context.getAspectMethodContext();
    final AspectFactory aspectFactory = context
      .getAspectDefinition()
      .findAspectFactoryFor(interceptor.getDeclaringType())
      .get();

    final HxMethod aspectFactoryMethod = aspectFactory.getMethod();
    final HxExtendedCodeStream stream = anchor.asAnchoredStream();
    final String aspectAttributeName = aspectFactory.getAspectAttributeName();
    final Optional<ParameterInjectorSelector.Result> injectors =
      getParameterInjectorSelector().selectSuitableInjectors(context, original, aspectFactoryMethod);

    if (!injectors.isPresent()) {
      return false;
    }

    if (!methodContext.hasLocalAttribute(aspectAttributeName)) {
      HxType aspectType;
      if (aspectFactoryMethod.isConstructor()) {
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
                       anchor,
                       injectors.get());

      stream.INVOKE(aspectFactoryMethod);

      if (!aspectFactory.isReusable()) {
        return true;
      }

      final int slotIndex = methodContext.getNextSlotIndex();
      HxCgenUtils.shiftAccessToLocalVariable(methodContext.getBegin(), slotIndex, 1);
      methodContext.createLocalAttribute(aspectType, aspectAttributeName, slotIndex);
      stream.GENERIC_STORE(aspectType, slotIndex);
    }

    final AspectLocalAttribute localAttribute = methodContext.getLocalAttribute(aspectAttributeName);
    stream.GENERIC_LOAD(localAttribute.getType(), localAttribute.getIndex());
    return true;
  }
}
