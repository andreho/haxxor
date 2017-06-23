package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdviceResultHandler;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
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
public abstract class AbstractAspectAdviceType
  implements AspectAdviceType {

  protected  <E extends HxAnnotated<E> & HxOrdered>
  Integer getIndex(final E element) {
    return element.getAnnotation(Constants.ORDER_ANNOTATION_TYPE)
                  .map(a -> a.getAttribute("value", Integer.MAX_VALUE))
                  .orElse(Constants.UNORDERED_ELEMENT_INDEX + element.getIndex());
  }

  private final int order;
  private final AspectAdviceParameterInjector parameterInjector;
  private final AspectAdviceResultHandler resultHandler;

  public AbstractAspectAdviceType(final int order,
                                  final AspectAdviceParameterInjector parameterInjector,
                                  final AspectAdviceResultHandler resultHandler) {
    this.order = order;
    this.parameterInjector = parameterInjector;
    this.resultHandler = resultHandler;
  }

  @Override
  public int getOrder() {
    return order;
  }

  @Override
  public AspectAdviceParameterInjector getParameterInjector() {
    return parameterInjector;
  }

  @Override
  public AspectAdviceResultHandler getResultHandler() {
    return resultHandler;
  }

  @Override
  public boolean hasTarget(final AspectAdvice.Target target) {
    return false;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
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
