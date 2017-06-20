package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectApplicationContext;
import net.andreho.aop.spi.AspectAttribute;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.cgen.impl.InstructionCodeStream;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class AfterAspectStep
  extends AbstractCallingAspectStep<HxMethod> {

  public AfterAspectStep(final int index,
                         final AspectStepType type,
                         final String profileName,
                         final ElementMatcher<HxMethod> elementMatcher,
                         final HxMethod interceptor) {
    super(index, type, elementMatcher, profileName, interceptor);
  }

  @Override
  public boolean needsAspectFactory() {
    return !getInterceptor().isStatic();
  }

  @Override
  public boolean apply(final AspectApplicationContext ctx,
                       final HxMethod intercepted) {
    if (!match(intercepted) || !intercepted.hasCode()) {
      return false;
    }

    System.out.println("@After[" + getIndex() + "]: " + intercepted);

    final HxCode code = intercepted.getCode();
    final HxMethod interceptor = getInterceptor();
    final HxInstructionFactory instructionFactory = code.getInstructionFactory();

    code.getFirst().forEachNext(
      ins -> ins.has(HxInstructionSort.Exit) && ins.hasNot(HxInstructionsType.Exit.ATHROW),
      ins -> onReturnOpcode(ctx, instructionFactory, interceptor, intercepted, ins.getPrevious())
    );

    return true;
  }

  private void onReturnOpcode(final AspectApplicationContext ctx,
                              final HxInstructionFactory insFactory,
                              final HxMethod interceptor,
                              final HxMethod intercepted,
                              final HxInstruction instruction) {

    final InstructionCodeStream codeStream =
      instruction.asStream(ins -> new InstructionCodeStream(ins, insFactory));

    handleInjectableParameters(ctx, interceptor, intercepted, codeStream);

    codeStream.INVOKESTATIC(interceptor);

    handleReturnValue(ctx, interceptor, intercepted, codeStream);
  }

  private void handleReturnValue(final AspectApplicationContext context,
                                 final HxMethod interceptor,
                                 final HxMethod intercepted,
                                 final HxExtendedCodeStream codeStream) {

    final HxType returnType = interceptor.getReturnType();
    switch (returnType.getSlotsCount()) {
      case 1:
        codeStream.POP();
        break;
      case 2:
        codeStream.POP2();
        break;
    }
  }

  private void handleInjectableParameters(final AspectApplicationContext context,
                                          final HxMethod interceptor,
                                          final HxMethod intercepted,
                                          final HxExtendedCodeStream codeStream) {

    for (HxParameter parameter : interceptor.getParameters()) {
      if(parameter.isAnnotationPresent(Attribute.class)) {
        handleAttributeInjection(context, parameter, intercepted, codeStream);
      }
    }
  }

  private void handleAttributeInjection(final AspectApplicationContext context,
                                        final HxParameter parameter,
                                        final HxMethod intercepted,
                                        final HxExtendedCodeStream codeStream) {
    if(parameter.isAnnotationPresent(Attribute.class)) {
      final HxAnnotation attributeAnnotation = parameter.getAnnotation(Attribute.class).get();
      final String attributeName = attributeAnnotation.getAttribute("value", "");

      if(attributeName.isEmpty()) {
        throw new IllegalStateException("Attribute's name is invalid.");
      }

      if(!context.hasLocalAttribute(attributeName)) {
        throw new IllegalStateException("There isn't attribute with name: "+attributeName);
      }

      final AspectAttribute attribute = context.getLocalAttribute(attributeName);
      HxCgenUtils.genericLoadSlot(attribute.getType(), attribute.getIndex(), codeStream);
    }
  }

  @Override
  public String toString() {
    return "AfterAspectStep{}";
  }
}
