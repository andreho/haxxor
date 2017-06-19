package net.andreho.aop.spi;

import net.andreho.aop.spi.impl.steps.AfterAspectStepType;
import net.andreho.aop.spi.impl.steps.BeforeAspectStepType;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Collection;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:33.
 */
public enum DefaultAspectStepTypes
  implements AspectStepType {
//  MODIFY_TYPE,
//  MODIFY_FIELD,
//  MODIFY_METHOD,
//  FORWARDING,
  //  PATCH,
  BEFORE(new BeforeAspectStepType()),
  AFTER(new AfterAspectStepType()),
//  FINALLY,
//  CATCH,
//  FIELD_GET,
//  FIELD_SET
  ;
  private final AspectStepType delegate;

  DefaultAspectStepTypes(final AspectStepType delegate) {
    this.delegate = delegate;
  }


  @Override
  public boolean hasKind(final AspectStep.Kind kind) {
    return delegate.hasKind(kind);
  }

  @Override
  public Collection<AspectStep<?>> buildSteps(final AspectDefinition def, final HxType type) {
    return delegate.buildSteps(def, type);
  }
}
