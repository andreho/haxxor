package net.andreho.aop.spi.impl.advices.injectors;

import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.spi.AspectAdvice;
import net.andreho.aop.spi.AspectContext;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class DeclaringParameterInjector
  extends AbstractAnnotatedParameterInjector {
  private static final String HELPERS_CLASS = "net/andreho/aop/spi/impl/advices/injectors/Helpers";
  public static final DeclaringParameterInjector INSTANCE = new DeclaringParameterInjector();

  public DeclaringParameterInjector() {
    super(Declaring.class.getName());
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

    if(original.isStatic()) {
      stream.TYPE(original.getDeclaringMember());
    } else {
      stream.THIS().INVOKESTATIC(HELPERS_CLASS,
                                 "getClassOf",
                                 "(Ljava/lang/Object;)Ljava/lang/Class;", false);
    }

    return true;
  }
/*
INVOKEVIRTUAL java/lang/Object.getClass ()Ljava/lang/Class;
*/
}
