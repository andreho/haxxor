package org.sandbox.aspects.mixin;

import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.mixin.Mixin;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import org.sandbox.mixin.DynamicallyComposedClass;
import org.sandbox.mixin.SpecificMixin;

import static org.sandbox.aspects.mixin.MixinApplyingAspect.SELECTOR_NAME;

/**
 * <br/>Created by a.hofmann on 30.09.2017 at 04:02.
 */
@Aspect(SELECTOR_NAME)
@Profile(name = SELECTOR_NAME, classes = @Classes(@ClassWith(DynamicallyComposedClass.class)))
@Mixin.Application(value = SELECTOR_NAME, mixins = SpecificMixin.class)
public class MixinApplyingAspect {
  public static final String SELECTOR_NAME = "DynamicallyComposedClass";
}
