package net.andreho.aop.spi.impl.advices.results;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.ResultPostProcessor;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:45.
 */
public class DefaultPostProcessor
  implements ResultPostProcessor {
  public static final DefaultPostProcessor INSTANCE = new DefaultPostProcessor();

  @Override
  public boolean handle(final AspectAdvice<?> aspectAdvice,
                        final AspectContext context,
                        final HxMethod interceptor,
                        final HxMethod original,
                        final HxMethod shadow,
                        final HxInstruction anchor) {

    switch (interceptor.getReturnType().getSlotSize()) {
      case 1:
        anchor.asAnchoredStream().POP();
        break;
      case 2:
        anchor.asAnchoredStream().POP2();
        break;
    }
    return true;
  }
}
