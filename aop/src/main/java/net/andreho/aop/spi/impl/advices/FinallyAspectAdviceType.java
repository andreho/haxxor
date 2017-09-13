package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.After;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.aop.spi.impl.advices.injectors.ArgParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ArgsParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ArityParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.AttributeParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.CaughtParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.DeclaringParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.DefaultInjector;
import net.andreho.aop.spi.impl.advices.injectors.InterceptedParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.LineParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.MarkerParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ThisParameterInjector;
import net.andreho.aop.spi.impl.advices.results.DefaultPostProcessor;
import net.andreho.aop.spi.impl.advices.results.LocalAttributePostProcessor;
import net.andreho.aop.spi.impl.advices.results.RedefinePostProcessor;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class FinallyAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.METHOD, AspectAdvice.Target.CONSTRUCTOR
  };

  public FinallyAspectAdviceType(final int order) {
    super(
      order,
      AspectAdviceParameterInjector.with(
        ArgParameterInjector.INSTANCE,
        ArgsParameterInjector.INSTANCE,
        ThisParameterInjector.INSTANCE,
        InterceptedParameterInjector.INSTANCE,
        DeclaringParameterInjector.INSTANCE,
        LineParameterInjector.INSTANCE,
        ArityParameterInjector.INSTANCE,
        MarkerParameterInjector.INSTANCE,
        AttributeParameterInjector.INSTANCE,
        CaughtParameterInjector.INSTANCE,
        DefaultInjector.INSTANCE
      ),
      AspectAdvicePostProcessor.with(
        RedefinePostProcessor.INSTANCE,
        LocalAttributePostProcessor.INSTANCE,
        DefaultPostProcessor.INSTANCE
      ),
      TARGETS
    );
  }

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return activatorAnnotation == After.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> finallyAdvices = locateAdvicesWith(type, Constants.FINALLY_ANNOTATION_TYPE);

    if (finallyAdvices.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

    for (HxMethod finallyAdvice : finallyAdvices) {
      final int index = getIndex(finallyAdvice);
      final HxAnnotation finallyAnnotation = finallyAdvice.getAnnotation(Constants.FINALLY_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(finallyAnnotation);
      final HxType exceptionType = null; //finallyAnnotation.getAttribute("exception", (HxType) null);
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, profileName);

      steps.add(
        new FinallyAspectAdvice(
          index, profileName, this, affectedMethodsMatcher, finallyAdvice
        )
      );
    }
    return steps;
  }
}
