package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepResultHandler;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.aop.spi.impl.steps.injectors.ArgsInjector;
import net.andreho.aop.spi.impl.steps.injectors.ArityInjector;
import net.andreho.aop.spi.impl.steps.injectors.DeclaringInjector;
import net.andreho.aop.spi.impl.steps.injectors.DefaultInjector;
import net.andreho.aop.spi.impl.steps.injectors.InterceptedInjector;
import net.andreho.aop.spi.impl.steps.injectors.LineInjector;
import net.andreho.aop.spi.impl.steps.injectors.LocalAttributeInjector;
import net.andreho.aop.spi.impl.steps.injectors.ThisInjector;
import net.andreho.aop.spi.impl.steps.results.DefaultResultHandler;
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
public class AfterAspectStepType
  extends AbstractAspectStepType {

  public AfterAspectStepType(final int order) {
    super(
      order,
        AspectStepParameterInjector.with(
          ArgsInjector.INSTANCE,
          ThisInjector.INSTANCE,
          InterceptedInjector.INSTANCE,
          DeclaringInjector.INSTANCE,
          LineInjector.INSTANCE,
          ArityInjector.INSTANCE,
          LocalAttributeInjector.INSTANCE,
          DefaultInjector.INSTANCE
        ),
        AspectStepResultHandler.with(
          DefaultResultHandler.INSTANCE
        )
    );
  }

  @Override
  public boolean hasTarget(final AspectStep.Target target) {
    return target == AspectStep.Target.METHOD;
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> afterJoinPoints = locateJoinPointsWith(type, Constants.AFTER_ANNOTATION_TYPE);

    if (afterJoinPoints.isEmpty()) {
      return Collections.emptySet();
    }

    final List<AspectStep<?>> steps = new ArrayList<>();

    for (HxMethod afterJp : afterJoinPoints) {
      final int index = getIndex(afterJp);
      final HxAnnotation afterAnnotation = afterJp.getAnnotation(Constants.AFTER_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(afterAnnotation);
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, afterAnnotation, profileName);

      steps.add(new AfterAspectStep(index, this, profileName, affectedMethodsMatcher, afterJp));
    }
    return steps;
  }
}
