package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.Before;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdviceType;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ThisParameterInjector
  extends AbstractAnnotatedParameterInjector {

  public static final ThisParameterInjector INSTANCE = new ThisParameterInjector();

  public ThisParameterInjector() {
    super(This.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                              final AspectContext context,
                                              final HxMethod interceptor,
                                              final HxMethod original,
                                              final HxMethod shadow,
                                              final HxParameter parameter,
                                              final HxInstruction anchor) {

    if (!original.isStatic()) {
      final AspectAdviceType adviceType = aspectAdvice.getType();
      //don't allow an uninitialized instance to be used elsewhere: uninitializedThis
      if (!original.isConstructor() ||
          !adviceType.isActivatedThrough(Before.class)) {
        anchor.asAnchoredStream().THIS();
        return true;
      }
    }

    return false;
  }
}
