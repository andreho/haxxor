package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdviceResultHandler;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.aop.spi.impl.advices.injectors.ArgsParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ArityParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.DeclaringParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.DefaultInjector;
import net.andreho.aop.spi.impl.advices.injectors.InterceptedParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.LineParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.LocalAttributeParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ResultParameterInjector;
import net.andreho.aop.spi.impl.advices.injectors.ThisParameterInjector;
import net.andreho.aop.spi.impl.advices.results.DefaultResultHandler;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class AfterAspectAdviceType
  extends AbstractAspectAdviceType {

  public AfterAspectAdviceType(final int order) {
    super(
      order,
      AspectAdviceParameterInjector.with(
        ArgsParameterInjector.INSTANCE,
        ThisParameterInjector.INSTANCE,
        InterceptedParameterInjector.INSTANCE,
        DeclaringParameterInjector.INSTANCE,
        LineParameterInjector.INSTANCE,
        ArityParameterInjector.INSTANCE,
        LocalAttributeParameterInjector.INSTANCE,
        ResultParameterInjector.INSTANCE,
        DefaultInjector.INSTANCE
        ),
      AspectAdviceResultHandler.with(
          DefaultResultHandler.INSTANCE
        )
    );
  }

  @Override
  public boolean hasTarget(final AspectAdvice.Target target) {
    return target == AspectAdvice.Target.METHOD;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> afterJoinPoints = locateJoinPointsWith(type, Constants.AFTER_ANNOTATION_TYPE);

    if (afterJoinPoints.isEmpty()) {
      return Collections.emptySet();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

    for (HxMethod afterJp : afterJoinPoints) {
      final int index = getIndex(afterJp);
      final HxAnnotation afterAnnotation = afterJp.getAnnotation(Constants.AFTER_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(afterAnnotation);
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, afterAnnotation, profileName);

      steps.add(new AfterAspectAdvice(index, this, profileName, affectedMethodsMatcher, afterJp));
    }
    return steps;
  }
}
