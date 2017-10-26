package net.andreho.aop.spi.impl.advices;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.api.HxAnnotated;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxField;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxOrdered;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

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
  private final ParameterInjectorSelector parameterInjectorSelector;
  private final ResultPostProcessor resultPostProcessor;

  public AbstractAspectAdviceType(final int order,
                                  final ParameterInjectorSelector parameterInjectorSelector,
                                  final ResultPostProcessor resultPostProcessor,
                                  final AspectAdvice.Target... targets) {
    this.order = order;
    this.parameterInjectorSelector = requireNonNull(parameterInjectorSelector);
    this.resultPostProcessor = requireNonNull(resultPostProcessor);
    this.targets = EnumSet.copyOf(Arrays.asList(targets));
  }

  @Override
  public int getOrder() {
    return order;
  }

  @Override
  public ParameterInjectorSelector getParameterInjectorSelector() {
    return parameterInjectorSelector;
  }

  @Override
  public ResultPostProcessor getResultPostProcessor() {
    return resultPostProcessor;
  }

  @Override
  public boolean hasTarget(final AspectAdvice.Target target) {
    return targets.contains(target);
  }

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return false;
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

  protected ElementMatcher<HxParameter> obtainParametersMatcher(final AspectDefinition def,
                                                             final String profileName) {
    final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);
    return aspectProfile.orElseThrow(IllegalStateException::new).getParametersMatcher();
  }

  protected ElementMatcher<HxField> obtainFieldsMatcher(final AspectDefinition def,
                                                        final String profileName) {
    final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);
    return aspectProfile.orElseThrow(IllegalStateException::new).getFieldsMatcher();
  }

  protected Map<String, List<HxMethod>> collectAdvicesByProfileWithType(final String adviceAnnotation, final Collection<HxMethod> afterAdvices) {
    final Map<String, List<HxMethod>> mappedWithProfiles = new LinkedHashMap<>();

    for (HxMethod afterAdvice : afterAdvices) {
      final HxAnnotation afterAnnotation = afterAdvice.getAnnotation(adviceAnnotation).get();
      final String profile = fetchProfileName(afterAnnotation);
      final List<HxMethod> advicesByProfile = mappedWithProfiles.computeIfAbsent(profile,
                                                                                 (key) -> new ArrayList<>());

      advicesByProfile.add(afterAdvice);
    }
    return mappedWithProfiles;
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
