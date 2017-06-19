package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.spec.api.HxAnnotated;
import net.andreho.haxxor.spec.api.HxOrdered;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:35.
 */
public abstract class AbstractAspectStepType
  implements AspectStepType {

  protected  <E extends HxAnnotated<E> & HxOrdered>
  Integer getOrder(final E element) {
    return element.getAnnotation(Constants.ORDER_ANNOTATION_TYPE)
                  .map(a -> a.getAttribute("value", Integer.MAX_VALUE))
                  .orElse(Constants.UNORDERED_ELEMENT_OFFSET + element.getIndex());
  }

  @Override
  public boolean hasKind(final AspectStep.Kind kind) {
    return false;
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def, final HxType type) {
    throw new UnsupportedOperationException();
  }
}
