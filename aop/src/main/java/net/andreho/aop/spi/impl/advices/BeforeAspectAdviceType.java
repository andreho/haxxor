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
import net.andreho.aop.spi.impl.advices.injectors.ThisParameterInjector;
import net.andreho.aop.spi.impl.advices.results.DefaultResultHandler;
import net.andreho.aop.spi.impl.advices.results.LocalAttributeResultHandler;
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
public class BeforeAspectAdviceType
  extends AbstractAspectAdviceType {

  public BeforeAspectAdviceType(final int order) {
    super(
      order,
      AspectAdviceParameterInjector.with(
        ArgsParameterInjector.INSTANCE,
        ThisParameterInjector.INSTANCE,
        InterceptedParameterInjector.INSTANCE,
        DeclaringParameterInjector.INSTANCE,
        ArityParameterInjector.INSTANCE,
        LineParameterInjector.INSTANCE,
        DefaultInjector.INSTANCE
      ),
      AspectAdviceResultHandler.with(
        LocalAttributeResultHandler.INSTANCE,
        DefaultResultHandler.INSTANCE
      )
    );
  }

  @Override
  public boolean hasTarget(final AspectAdvice.Target target) {
    return target == AspectAdvice.Target.METHOD;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def,
                                                  final HxType type) {
    final Collection<HxMethod> beforeJoinPoints = locateJoinPointsWith(type, Constants.BEFORE_ANNOTATION_TYPE);

    if (beforeJoinPoints.isEmpty()) {
      return Collections.emptySet();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

    for (HxMethod beforeJp : beforeJoinPoints) {
      final int index = getIndex(beforeJp);
      final HxAnnotation afterAnnotation = beforeJp.getAnnotation(Constants.BEFORE_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(afterAnnotation);
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, afterAnnotation, profileName);

      steps.add(new BeforeAspectAdvice(index, profileName, this, affectedMethodsMatcher, beforeJp));
    }
    return steps;
  }
}
