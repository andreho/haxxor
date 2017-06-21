package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepResultHandler;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxOrdered;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;
import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public abstract class AbstractAspectStepType
  implements AspectStepType {

  protected  <E extends HxAnnotated<E> & HxOrdered>
  Integer getIndex(final E element) {
    return element.getAnnotation(Constants.ORDER_ANNOTATION_TYPE)
                  .map(a -> a.getAttribute("value", Integer.MAX_VALUE))
                  .orElse(Constants.UNORDERED_ELEMENT_INDEX + element.getIndex());
  }

  private final int order;
  private final AspectStepParameterInjector parameterInjector;
  private final AspectStepResultHandler resultHandler;

  public AbstractAspectStepType(final int order,
                                final AspectStepParameterInjector parameterInjector,
                                final AspectStepResultHandler resultHandler) {
    this.order = order;
    this.parameterInjector = parameterInjector;
    this.resultHandler = resultHandler;
  }

  @Override
  public int getOrder() {
    return order;
  }

  @Override
  public AspectStepParameterInjector getParameterInjector() {
    return parameterInjector;
  }

  @Override
  public AspectStepResultHandler getResultHandler() {
    return resultHandler;
  }

  @Override
  public boolean hasTarget(final AspectStep.Target target) {
    return false;
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def, final HxType type) {
    throw new UnsupportedOperationException();
  }

  protected Collection<HxMethod> locateJoinPointsWith(final HxType type, final String markerAnnotationType) {
    return type.methods(m ->
                          m.isPublic() &&
                          m.isAnnotationPresent(markerAnnotationType));
  }

  protected ElementMatcher<HxMethod> obtainMethodsMatcher(final AspectDefinition def,
                                                          final HxAnnotation annotation,
                                                          final String profileName) {
    return obtainMethodsMatcher(def, profileName, annotation.getAttribute("methods", Constants.EMPTY_ANNOTATION_ARRAY));
  }

  protected ElementMatcher<HxMethod> obtainMethodsMatcher(final AspectDefinition def,
                                                          final String profileName,
                                                          final HxAnnotation[] methods) {
    ElementMatcher<HxMethod> affectedMethodsMatcher;

    if(!profileName.isEmpty()) {
      final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);
      affectedMethodsMatcher = aspectProfile.orElseThrow(IllegalStateException::new).getMethodsMatcher();
    } else {
      affectedMethodsMatcher = def.getElementMatcherFactory().createMethodsFilter(methods);
    }
    return affectedMethodsMatcher;
  }

  protected String fetchProfileName(final HxAnnotation annotation) {
    return annotation.getAttribute("profile", "");
  }
}
