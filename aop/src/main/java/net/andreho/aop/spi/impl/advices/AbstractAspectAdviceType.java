package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceParameterInjector;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
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
  private final EnumSet<AspectAdvice.Target> targets;
  private final AspectAdviceParameterInjector parameterInjector;
  private final AspectAdvicePostProcessor resultHandler;

  public AbstractAspectAdviceType(final int order,
                                  final AspectAdviceParameterInjector parameterInjector,
                                  final AspectAdvicePostProcessor resultHandler,
                                  final AspectAdvice.Target ... targets) {
    this.order = order;
    this.parameterInjector = parameterInjector;
    this.resultHandler = resultHandler;
    this.targets = EnumSet.copyOf(Arrays.asList(targets));
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
  public AspectAdvicePostProcessor getPostProcessor() {
    return resultHandler;
  }

  @Override
  public boolean hasTarget(final AspectAdvice.Target target) {
    return targets.contains(target);
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    throw new UnsupportedOperationException();
  }

  protected Collection<HxMethod> locateAdvicesWith(final HxType type, final String markerAnnotationType) {
    return type.methods(m ->
                          m.isPublic() &&
                          m.isAnnotationPresent(markerAnnotationType));
  }

  protected ElementMatcher<HxMethod> obtainMethodsMatcher(final AspectDefinition def,
                                                          final String profileName) {
    final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);
    return aspectProfile.orElseThrow(IllegalStateException::new).getMethodsMatcher();
  }

  protected String fetchProfileName(final HxAnnotation annotation, final String attribute) {
    String profileName = annotation.getAttribute(attribute, "");
    if(profileName == null || profileName.isEmpty()) {
      throw new IllegalStateException("Invalid name of referenced profile: "+profileName);
    }
    return profileName;
  }

  protected String fetchProfileName(final HxAnnotation annotation) {
    return fetchProfileName(annotation, "value");
  }
}
