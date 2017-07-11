package net.andreho.aop.spi.impl.advices.results;

import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAdvicePostProcessor;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxSort;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:45.
 */
public class TryCatchPostProcessor
  implements AspectAdvicePostProcessor {
  public static final TryCatchPostProcessor INSTANCE = new TryCatchPostProcessor();

  @Override
  public boolean process(final AspectAdvice<?> aspectAdvice,
                         final AspectContext context,
                         final HxMethod interceptor,
                         final HxMethod original,
                         final HxMethod shadow,
                         final HxInstruction anchor) {

    final HxSort returnSort = original.getReturnType().getSort();
    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    switch (returnSort) {
      case VOID:
        stream.RETURN();
      case BOOLEAN:
      case BYTE:
      case SHORT:
      case CHAR:
      case INT:
      case FLOAT:
      case LONG:
      case DOUBLE:
    }

    switch (interceptor.getReturnType().getSlotSize()) {
      case 1:
        stream.POP();
        break;
      case 2:
        stream.POP2();
        break;
    }
    return true;
  }
}
