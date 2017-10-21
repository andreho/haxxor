package net.andreho.aop.spi;

import net.andreho.aop.api.After;
import net.andreho.aop.api.Before;
import net.andreho.aop.api.Catch;
import net.andreho.aop.api.Modify;
import net.andreho.aop.api.Order;
import net.andreho.aop.api.mixin.Mixin;
import net.andreho.aop.spi.impl.advices.AfterAspectAdviceType;
import net.andreho.aop.spi.impl.advices.BeforeAspectAdviceType;
import net.andreho.aop.spi.impl.advices.CatchAspectAdviceType;
import net.andreho.aop.spi.impl.mixin.MixinAspectAdviceType;
import net.andreho.aop.spi.impl.modify.ModifyTypeAspectAdviceType;

import java.lang.annotation.Annotation;

/**
 * <br/>Created by a.hofmann on 17.06.2017 at 07:33.
 */
public enum DefaultAspectStepTypes {
  MIXIN(new MixinAspectAdviceType(fetchOrderFromAnnotation(Mixin.Application.class))),
  MODIFY_TYPE(new ModifyTypeAspectAdviceType(fetchOrderFromAnnotation(Modify.Type.class))),
//  MODIFY_FIELD,
//  MODIFY_METHOD,
//  FORWARDING,
  BEFORE(new BeforeAspectAdviceType(fetchOrderFromAnnotation(Before.class))),
  AFTER(new AfterAspectAdviceType(fetchOrderFromAnnotation(After.class))),
  CATCH(new CatchAspectAdviceType(fetchOrderFromAnnotation(Catch.class))),
//  FINALLY(new FinallyAspectAdviceType(fetchOrderFromAnnotation(Finally.class))),
//  FIELD_GET,
//  FIELD_SET
  ;
  private final AspectAdviceType stepType;

  DefaultAspectStepTypes(final AspectAdviceType stepType) {
    this.stepType = stepType;
  }

  private static int fetchOrderFromAnnotation(final Class<? extends Annotation> annotation) {
    return annotation.getAnnotation(Order.class).value();
  }

  public AspectAdviceType getStepType() {
    return stepType;
  }
}
