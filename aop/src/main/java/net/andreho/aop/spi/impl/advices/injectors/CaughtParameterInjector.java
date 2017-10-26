package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.Catch;
import net.andreho.aop.api.injectable.Caught;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectTryCatch;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class CaughtParameterInjector
  extends AbstractAnnotatedParameterInjector {
  public static final CaughtParameterInjector INSTANCE = new CaughtParameterInjector();

  public CaughtParameterInjector() {
    super(Caught.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction anchor) {

    final HxType exception =
      interceptor.getAnnotation(Catch.class).get()
                 .getAttribute("exception", HxType.class);

    final AspectTryCatch tryCatchBlock =
      context.getAspectMethodContext().getTryCatchBlock(exception.getName());

    anchor.asAnchoredStream()
          .ALOAD(tryCatchBlock.getHxLocalVariable().getIndex());

    return true;
  }
}
