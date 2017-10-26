package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgsParameterInjector
  extends AbstractAnnotatedParameterInjector {
  public static final ArgsParameterInjector INSTANCE = new ArgsParameterInjector();

  public ArgsParameterInjector() {
    super(Args.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction anchor) {

    anchor.asAnchoredStream()
          .PACK_ARGUMENTS(original.getParameterTypes(), original.isStatic()? 0 : 1);
    return true;
  }
}
