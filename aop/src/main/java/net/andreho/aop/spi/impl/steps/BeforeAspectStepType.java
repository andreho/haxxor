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
public class BeforeAspectStepType
  extends AbstractAspectStepType {

  @Override
  public boolean hasKind(final AspectStep.Kind kind) {
    return kind == AspectStep.Kind.METHOD;
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def,
                                              final HxType type) {
    final Collection<HxMethod> beforeJoinPoints =
      type.methods(m ->
                     m.isPublic() &&
                     m.isAnnotationPresent(Constants.BEFORE_ANNOTATION_TYPE));

    if (beforeJoinPoints.isEmpty()) {
      return Collections.emptySet();
    }

    final List<AspectStep<?>> steps = new ArrayList<>();

    for (HxMethod beforeJp : beforeJoinPoints) {
      final int index = getOrder(beforeJp);

      final HxAnnotation beforeAnnotation = beforeJp.getAnnotation(Constants.BEFORE_ANNOTATION_TYPE).get();
      final String profileName = beforeAnnotation.getAttribute("profile", "");
      final HxAnnotation[] methods = beforeAnnotation.getAttribute("methods", Constants.EMPTY_ANNOTATION_ARRAY);
      final ElementMatcher<HxMethod> affectedMethodsMatcher =
        def.getElementMatcherFactory().create(beforeJp, methods);

      steps.add(new BeforeAspectStep(index, this, affectedMethodsMatcher, profileName, beforeJp));
    }

    return steps;
  }
}
