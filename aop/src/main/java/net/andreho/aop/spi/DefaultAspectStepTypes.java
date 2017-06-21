package net.andreho.aop.spi;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Order;
import net.andreho.aop.spi.impl.steps.AfterAspectStepType;
import net.andreho.aop.spi.impl.steps.BeforeAspectStepType;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:33.
 */
public enum DefaultAspectStepTypes {
//  MODIFY_TYPE,
//  MODIFY_FIELD,
//  MODIFY_METHOD,
//  FORWARDING,
  //  PATCH,
  BEFORE(new BeforeAspectStepType(fetchOrderFromAnnotation(Before.class))),
  AFTER(new AfterAspectStepType(fetchOrderFromAnnotation(After.class))),
//  FINALLY,
//  CATCH,
//  FIELD_GET,
//  FIELD_SET
  ;
  private final AspectStepType stepType;

  DefaultAspectStepTypes(final AspectStepType stepType) {
    this.stepType = stepType;
  }

  private static int fetchOrderFromAnnotation(final Class<? extends Annotation> annotation) {
    return annotation.getAnnotation(Order.class).value();
  }

  public AspectStepType getStepType() {
    return stepType;
  }
}
