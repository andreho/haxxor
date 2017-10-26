package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.api.Before;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class BeforeAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.METHOD, AspectAdvice.Target.CONSTRUCTOR
  };

  public BeforeAspectAdviceType(final int order) {
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
    return activatorAnnotation == Before.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> afterAdvices = locateAdvicesWith(type, Constants.BEFORE_ANNOTATION_TYPE);

    if (afterAdvices.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();
    final Map<String, List<HxMethod>> mappedWithProfiles = new LinkedHashMap<>();

    for (HxMethod afterAdvice : afterAdvices) {
      final HxAnnotation afterAnnotation = afterAdvice.getAnnotation(Constants.BEFORE_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(afterAnnotation);
      final List<HxMethod> advicesByProfile = mappedWithProfiles.computeIfAbsent(profileName, (key) -> new ArrayList<>());

      advicesByProfile.add(afterAdvice);
    }

    for (final Map.Entry<String, List<HxMethod>> entry : mappedWithProfiles.entrySet()) {
      final String profileName = entry.getKey();
      final List<HxMethod> aspects = entry.getValue();
      final ElementMatcher<HxMethod> affectedMethodsMatcher = obtainMethodsMatcher(def, profileName);
      steps.add(new BeforeAspectAdvice(profileName, this, affectedMethodsMatcher, aspects));
    }
    return steps;
  }
}
