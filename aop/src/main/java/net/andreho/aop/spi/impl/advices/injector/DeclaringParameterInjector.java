package net.andreho.aop.spi.impl.advices.injector;

import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.api.HxMethod;
import net.andreho.haxxor.api.HxParameter;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class DeclaringParameterInjector
  extends AbstractParameterInjector {
  private static final String HELPERS_CLASS = "net/andreho/aop/spi/Helpers";
  private static final Class<Class> CLASS_TYPE = Class.class;
  private static final Class<Declaring> DECLARING_ANNOTATION_TYPE = Declaring.class;
  public static final DeclaringParameterInjector INSTANCE = new DeclaringParameterInjector();

  public DeclaringParameterInjector() {
  }

  @Override
  public int score(final AspectContext context,
                   final HxMethod original,
                   final HxMethod interceptor,
                   final HxParameter parameter) {
    if(!parameter.isAnnotationPresent(DECLARING_ANNOTATION_TYPE)) {
      return NOT_INJECTABLE;
    }

    if(parameter.hasType(CLASS_TYPE)) {
      return MAX_SUITABLE;
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

    final HxExtendedCodeStream stream = anchor.asAnchoredStream();

    if(original.isStatic() ||
       original.isConstructor()) {
      stream.TYPE(original.getDeclaringMember());
    } else {
      stream.THIS().INVOKESTATIC(HELPERS_CLASS,
                                 "getClassOf",
                                 "(Ljava/lang/Object;)Ljava/lang/Class;", false);
    }
  }
}
