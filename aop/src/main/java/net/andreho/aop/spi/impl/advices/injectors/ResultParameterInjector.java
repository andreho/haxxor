package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectLocalAttribute;
import net.andreho.aop.spi.AspectMethodContext;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxSort;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ResultParameterInjector
  extends AbstractAnnotatedParameterInjector {

  public static final ResultParameterInjector INSTANCE = new ResultParameterInjector();
  private static final String RESULT_ATTRIBUTES_NAME = Constants.RESULT_ATTRIBUTES_NAME;

  public ResultParameterInjector() {
    super(Result.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction anchor) {

    if (!original.hasReturnType(HxSort.VOID)) {
      final HxExtendedCodeStream stream = anchor.asAnchoredStream();

      final AspectMethodContext methodContext = context.getAspectMethodContext();
      final AspectLocalAttribute attribute = methodContext.getLocalAttribute(RESULT_ATTRIBUTES_NAME);

      stream.GENERIC_LOAD(attribute.getType(), attribute.getIndex());

      if(!parameter.getType().isPrimitive() &&
         attribute.getType().isPrimitive()) {

        stream.AUTOBOXING(attribute.getType());
      }
      return true;
    }

    return false;
  }
}
