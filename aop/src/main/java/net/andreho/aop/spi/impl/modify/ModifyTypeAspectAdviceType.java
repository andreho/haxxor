package net.andreho.aop.spi.impl.modify;

import net.andreho.aop.api.Modify;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectProfile;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.ParameterInjectorSelector;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.aop.spi.impl.advices.AbstractAspectAdviceType;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxType;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.andreho.aop.spi.impl.Constants.ANY_SELECTION_PROFILE_NAME;
import static net.andreho.aop.spi.impl.Constants.MODIFY_TYPE_ANNOTATION_TYPE;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public class ModifyTypeAspectAdviceType
  extends AbstractAspectAdviceType {
  private static final AspectAdvice.Target[] TARGETS = {
    AspectAdvice.Target.TYPE
  };

  public ModifyTypeAspectAdviceType(final int order) {
    super(order,
          ParameterInjectorSelector.create(),
          ResultPostProcessor.list(),
          TARGETS
    );
  }

  @Override
  public boolean isActivatedThrough(final Class<? extends Annotation> activatorAnnotation) {
    return activatorAnnotation == Modify.Type.class;
  }

  @Override
  public Collection<AspectAdvice<?>> buildAdvices(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> modifyTypeAdvices = locateAdvicesWith(type, MODIFY_TYPE_ANNOTATION_TYPE);

    if (modifyTypeAdvices.isEmpty()) {
      return Collections.emptyList();
    }

    final List<AspectAdvice<?>> steps = new ArrayList<>();

    for (HxMethod advice : modifyTypeAdvices) {
      validateModifyMethod(advice);

      final int index = getIndex(advice);
      final HxAnnotation modifyAnnotation = advice.getAnnotation(MODIFY_TYPE_ANNOTATION_TYPE).get();
      final String profileName = fetchProfileName(modifyAnnotation);
      final Optional<AspectProfile> aspectProfile = def.getAspectProfile(profileName);

      final ElementMatcher<HxType> typeMatcher =
        ANY_SELECTION_PROFILE_NAME.equals(profileName)?
          ElementMatcher.any() :
        aspectProfile.orElseThrow(IllegalStateException::new).getClassesMatcher();

      steps.add(new ModifyTypeAspectAdvice(index, profileName, this, typeMatcher, advice));
    }
    return steps;
  }

  private void validateModifyMethod(HxMethod method) {
    if(!method.isStatic() ||
       !method.isPublic() ||
       !method.hasParametersCount(1) ||
       !method.hasParameterWithTypeAt(0, HxType.class) ||
       !method.hasReturnType(Boolean.TYPE)) {

      throw new IllegalArgumentException("Given method isn't suitable as @Modify.Type processor: "+method);
    }
  }
}
