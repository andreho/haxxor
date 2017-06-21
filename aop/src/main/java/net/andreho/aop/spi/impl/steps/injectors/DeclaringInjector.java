package net.andreho.aop.spi.impl.steps.injectors;

import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 00:49.
 */
public final class DeclaringInjector
  extends AbstractAnnotatedInjector {
  private static final String HELPERS_CLASS = "net/andreho/aop/spi/impl/steps/injectors/Helpers";
  public static final DeclaringInjector INSTANCE = new DeclaringInjector();

  public DeclaringInjector() {
    super(Declaring.class.getName());
  }

  @Override
  protected void checkedParameterInjection(final AspectStep<?> aspectStep,
                                           final AspectContext context,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxParameter parameter,
                                           final HxInstruction instruction) {
    final HxExtendedCodeStream stream = instruction.asStream();

    if(method.isStatic()) {
      stream.TYPE(method.getDeclaringMember());
    } else {
      stream.THIS().INVOKESTATIC(HELPERS_CLASS,
                                 "getClassOf",
                                 "(Ljava/lang/Object;)Ljava/lang/Class;", false);
    }
  }
/*
INVOKEVIRTUAL java/lang/Object.getClass ()Ljava/lang/Class;
*/
}
