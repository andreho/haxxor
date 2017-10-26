package net.andreho.aop.spi.impl.mixin;

import net.andreho.aop.api.StandardOrder;
import net.andreho.aop.api.mixin.Mixin;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.advices.AbstractAspectAdviceType;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static net.andreho.aop.spi.impl.Constants.ANY_SELECTION_PROFILE_NAME;
import static net.andreho.aop.spi.impl.Constants.EMPTY_TYPE_ARRAY;
import static net.andreho.aop.spi.impl.Constants.MIXIN_APPLICATION_ANNOTATION;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class MixinAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.TYPE
  };

  public MixinAspectAdviceType(final int order) {
    super(
      order,
      ParameterInjectorSelector.create(),
      ResultPostProcessor.list(),
      TARGETS
    );
  }

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return activatorAnnotation == Mixin.Application.class ||
           activatorAnnotation == Mixin.Applications.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {

    final Collection<HxAnnotation> mixinAnnotationCollection = type.getAnnotationsByType(MIXIN_APPLICATION_ANNOTATION);
    if(mixinAnnotationCollection.isEmpty()) {
      return Collections.emptyList();
    }

    final Collection<AspectAdvice<?>> result = new ArrayList<>();

    for(HxAnnotation mixinAnnotation : mixinAnnotationCollection) {
      final String profileName = fetchProfileName(mixinAnnotation);
      final HxType[] mixinTypes = mixinAnnotation.getAttribute("mixins", EMPTY_TYPE_ARRAY);

      if(profileName.isEmpty() || mixinTypes.length == 0) {
        continue;
      }

      final int index = StandardOrder.MIXIN;
      final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);
      final ElementMatcher<HxType> typeMatcher = ANY_SELECTION_PROFILE_NAME.equals(profileName)?
        ElementMatcher.any() :
        aspectProfile.orElseThrow(IllegalStateException::new).getClassesMatcher();

      result.add(new MixinAspectAdvice(index, profileName, this, typeMatcher, mixinTypes));
    }

    return result;
  }
}
