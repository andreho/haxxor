package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.ArgPassing;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.aop.spi.impl.advices.results.DefaultPostProcessor;
import net.andreho.aop.spi.impl.advices.results.LocalAttributePostProcessor;
import net.andreho.aop.spi.impl.advices.results.RedefinePostProcessor;
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
public class ArgPassingAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.PARAMETER
  };

  public ArgPassingAspectAdviceType(final int order) {
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
        DefaultInjector.INSTANCE
      )
   */

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return activatorAnnotation == ArgPassing.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> argPassingAdvices = locateAdvicesWith(type, Constants.ARG_PASSING_ANNOTATION_TYPE);

    if (argPassingAdvices.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

//    for (HxMethod argPassingAdvice : argPassingAdvices) {
//      final int index = getIndex(argPassingAdvice);
//      final HxAnnotation argPassingAnnotation = argPassingAdvice.getAnnotation(Constants.ARG_PASSING_ANNOTATION_TYPE).get();
//      final String profileName = fetchProfileName(argPassingAnnotation);
//      final ElementMatcher<HxParameter> affectedMethodsMatcher = obtainParametersMatcher(def, profileName);
//
//      steps.add(new ArgPassingAspectAdvice(index, profileName, this, affectedMethodsMatcher, argPassingAdvice));
//    }
    return steps;
  }
}
