package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class ArgParameterInjector
  extends AbstractAnnotatedParameterInjector {
  private static final Class<Arg> ANNOTATION_TYPE = Arg.class;
  public static final ArgParameterInjector INSTANCE = new ArgParameterInjector();

  public ArgParameterInjector() {
    super(Arg.class.getName());
  }

  @Override
  protected boolean checkedParameterInjection(final AspectAdvice<?> aspectAdvice,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod original,
                                           final HxMethod shadow,
                                           final HxParameter parameter,
                                           final HxInstruction anchor) {

    final HxAnnotation argAnnotation = parameter.getAnnotation(ANNOTATION_TYPE).get();
    int injectableIndex = argAnnotation.getAttribute("value", -1);

    if(injectableIndex == Arg.BY_TYPE) {
      int autoSelectedArgIndex = -1;
      for (HxParameter p : original.getParameters()) {
        if(p.getType().equals(parameter.getType())) {
          autoSelectedArgIndex = p.getIndex();
          break;
        }
      }
      injectableIndex = autoSelectedArgIndex;
    }

    if(injectableIndex > -1 && original.hasParameterAt(injectableIndex)) {
      final HxParameter injectableArg = original.getParameterAt(injectableIndex);
      final HxExtendedCodeStream stream = anchor.asAnchoredStream();

      stream
        .GENERIC_LOAD(injectableArg.getType().getSort(), original.getParametersSlotAt(injectableIndex))
        .CONVERT(injectableArg.getType(), parameter.getType());

      return true;
    }

    return false;
  }
}
