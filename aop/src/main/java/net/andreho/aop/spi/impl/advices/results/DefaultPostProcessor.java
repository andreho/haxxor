package net.andreho.aop.spi.impl.advices.results;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:45.
 */
public class DefaultPostProcessor
  implements AspectAdvicePostProcessor {
  public static final DefaultPostProcessor INSTANCE = new DefaultPostProcessor();

  @Override
  public boolean process(final AspectAdvice<?> aspectAdvice,
                         final AspectContext context,
                         final HxMethod interceptor,
                         final HxMethod original,
                         final HxMethod shadow,
                         final HxInstruction instruction) {

    switch (interceptor.getReturnType().getSlotSize()) {
      case 1:
        instruction.asStream().POP();
        break;
      case 2:
        instruction.asStream().POP2();
        break;
    }
    return true;
  }
}
