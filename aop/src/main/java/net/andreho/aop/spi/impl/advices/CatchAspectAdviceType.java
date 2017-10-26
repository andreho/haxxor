package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.After;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.aop.spi.impl.advices.results.DefaultPostProcessor;
import net.andreho.aop.spi.impl.advices.results.LocalAttributePostProcessor;
import net.andreho.aop.spi.impl.advices.results.RedefinePostProcessor;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class CatchAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.METHOD, AspectAdvice.Target.CONSTRUCTOR
  };

  public CatchAspectAdviceType(final int order) {
    super(
      order,
      ParameterInjectorSelector.create(),
      ResultPostProcessor.list(
        RedefinePostProcessor.INSTANCE,
        LocalAttributePostProcessor.INSTANCE,
        DefaultPostProcessor.INSTANCE
      ),
      TARGETS
    );
  }

  /*
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
      )
   */

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return activatorAnnotation == After.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> catchAdvices = locateAdvicesWith(type, Constants.CATCH_ANNOTATION_TYPE);

    if (catchAdvices.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

    for (HxMethod catchAdvice : catchAdvices) {
      final int index = getIndex(catchAdvice);
      final HxAnnotation catchAnnotation = catchAdvice.getAnnotation(Constants.CATCH_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(catchAnnotation);
      final HxType exceptionType = catchAnnotation.getAttribute("exception", (HxType) null);
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, profileName);

//      steps.add(
//        new CatchAspectAdvice(
//          index, profileName, this, affectedMethodsMatcher, catchAdvice, exceptionType
//        )
//      );
    }
    return steps;
  }
}
