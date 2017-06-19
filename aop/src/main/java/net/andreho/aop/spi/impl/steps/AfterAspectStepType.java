package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.aop.spi.impl.Constants;
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

  @Override
  public boolean hasKind(final AspectStep.Kind kind) {
    return kind == AspectStep.Kind.METHOD;
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def, final HxType type) {
    final Collection<HxMethod> afterJoinPoints =
      type.methods(m ->
                     m.isPublic() &&
                     m.isAnnotationPresent(Constants.AFTER_ANNOTATION_TYPE));

    if (afterJoinPoints.isEmpty()) {
      return Collections.emptySet();
    }

    final List<AspectStep<?>> steps = new ArrayList<>();

    for (HxMethod afterJp : afterJoinPoints) {
      final int index = getOrder(afterJp);
      final HxAnnotation afterAnnotation = afterJp.getAnnotation(Constants.AFTER_ANNOTATION_TYPE).get();
      final String profileName = afterAnnotation.getAttribute("profile", "");
      final HxAnnotation[] methods = afterAnnotation.getAttribute("methods", Constants.EMPTY_ANNOTATION_ARRAY);

      final ElementMatcher<HxMethod> affectedMethodsMatcher =
        def.getElementMatcherFactory().create(afterJp, methods);

      steps.add(new AfterAspectStep(index, this, affectedMethodsMatcher, profileName, afterJp));
    }
    return steps;
  }
}
