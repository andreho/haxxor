package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.injectable.Arg;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxAnnotation;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.api.HxType;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 06.10.2017 at 11:48.
 */
public class ArgParameterInjector extends AbstractParameterInjector {
  private static final Class<Arg> ARG_ANNOTATION_TYPE = Arg.class;
  public static final ArgParameterInjector INSTANCE = new ArgParameterInjector();

  public ArgParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {

    if(!parameter.isAnnotationPresent(ARG_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    final HxAnnotation argAnnotation = parameter.getAnnotation(ARG_ANNOTATION_TYPE).get();
    int injectableIndex = getInjectableIndex(original, parameter, argAnnotation);

    if(injectableIndex > -1 && original.hasParameterAt(injectableIndex)) {
      final HxParameter injectableArg = original.getParameterAt(injectableIndex);
      return scoreTypes(injectableArg.getType(), parameter.getType());
    }

    return NOT_INJECTABLE;
  }

  @Override
  public void inject(final AspectAdvice<?> aspectAdvice,
                     final AspectContext context,
                     final HxMethod original,
                     final HxMethod shadow,
                     final HxMethod interceptor,
                     final HxParameter parameter,
                     final HxInstruction anchor) {
    final HxAnnotation argAnnotation = parameter.getAnnotation(ARG_ANNOTATION_TYPE).get();
    int injectableIndex = getInjectableIndex(original, parameter, argAnnotation);

    if(injectableIndex > -1 && original.hasParameterAt(injectableIndex)) {
      final HxParameter injectableArg = original.getParameterAt(injectableIndex);
      final HxExtendedCodeStream stream = anchor.asAnchoredStream();
      final HxType injectableArgType = injectableArg.getType();

      stream
        .GENERIC_LOAD(injectableArgType.getSort(), original.getParametersSlotAt(injectableIndex))
        .CONVERT(injectableArgType, parameter.getType());
    }
  }

  private int getInjectableIndex(final HxMethod original,
                                 final HxParameter parameter,
                                 final HxAnnotation argAnnotation) {
    int injectableIndex = argAnnotation.getAttribute("value", Arg.BY_TYPE);

    if(injectableIndex > -1) {
      return injectableIndex;
    }

    for (HxParameter param : original.getParameters()) {
      if(param.getType().equals(parameter.getType())) {
        return param.getIndex();
      }
    }

    return NOT_INJECTABLE;
  }
}
