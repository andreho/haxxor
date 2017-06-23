package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Result;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.impl.Constants;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

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
                                           final HxInstruction instruction) {
    final HxExtendedCodeStream stream = instruction.asStream();

    if (original.hasReturnType("void")) {
      HxCgenUtils.genericLoadDefault(parameter.getType(), stream);
    } else {
      AspectAttribute attribute = context.getLocalAttribute(RESULT_ATTRIBUTES_NAME);
      HxCgenUtils.genericLoadSlot(attribute.getType(), attribute.getIndex(), stream);

      if(!parameter.getType().isPrimitive() && attribute.getType().isPrimitive()) {
        HxCgenUtils.wrapIfNeeded(attribute.getType(), stream);
      }
    }

    return true;
  }
}
