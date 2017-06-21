package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.HxInstructionsType;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

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
    super(index,
          type,
          elementMatcher,
          profileName,
          interceptor,
          type.getParameterInjector(),
          type.getResultHandler()
    );
  }

  @Override
  public boolean apply(final AspectContext ctx,
                       final HxMethod method) {
    if (!match(method) || !method.hasCode()) {
      return false;
    }

    System.out.println("@After[" + getIndex() + "]: " + method);

    final HxCode code = method.getCode();
    final HxMethod interceptor = getInterceptor();

    code.getFirst().forEachNext(
      ins -> ins.has(HxInstructionSort.Exit) && ins.hasNot(HxInstructionsType.Exit.ATHROW),
      ins -> handleReturnOpcode(ctx, interceptor, method, ins.getPrevious())
    );

    return true;
  }

  private void handleReturnOpcode(final AspectContext ctx,
                                  final HxMethod interceptor,
                                  final HxMethod method,
                                  final HxInstruction instruction) {

    final HxInstruction anchor = instruction.getNext();

    handleInterceptorParameters(ctx, interceptor, method, instruction);

    anchor.getPrevious().asStream().INVOKESTATIC(interceptor);

    handleReturnValue(ctx, interceptor, method, anchor.getPrevious());
  }

  private void handleReturnValue(final AspectContext context,
                                 final HxMethod interceptor,
                                 final HxMethod method,
                                 final HxInstruction instruction) {

    getResultHandler().handleReturnValue(this, context, interceptor, method, instruction);
  }

  private void handleInterceptorParameters(final AspectContext ctx,
                                           final HxMethod interceptor,
                                           final HxMethod method,
                                           final HxInstruction instruction) {

    final AspectStepParameterInjector parameterInjector = getParameterInjector();
    final HxInstruction anchor = instruction.getNext();

    for (HxParameter parameter : interceptor.getParameters()) {
      if (!parameterInjector.injectParameter(this, ctx, interceptor, method, parameter, anchor.getPrevious())) {
        throw new IllegalStateException(
          "Unable to inject parameter of " + interceptor + " at position: " + parameter.getIndex());
      }
    }
  }


//  private void handleInjectableParameters(final AspectContext context,
//                                          final HxMethod interceptor,
//                                          final HxMethod intercepted,
//                                          final HxExtendedCodeStream codeStream) {
//
//    for (HxParameter parameter : interceptor.getParameters()) {
//      if(parameter.isAnnotationPresent(Attribute.class)) {
//        handleAttributeInjection(context, parameter, intercepted, codeStream);
//      }
//    }
//  }
//
//  private void handleAttributeInjection(final AspectContext context,
//                                        final HxParameter parameter,
//                                        final HxMethod intercepted,
//                                        final HxExtendedCodeStream codeStream) {
//    if(parameter.isAnnotationPresent(Attribute.class)) {
//      final HxAnnotation attributeAnnotation = parameter.getAnnotation(Attribute.class).get();
//      final String attributeName = attributeAnnotation.getAttribute("value", "");
//
//      if(attributeName.isEmpty()) {
//        throw new IllegalStateException("Attribute's name is invalid.");
//      }
//
//      if(!context.hasLocalAttribute(attributeName)) {
//        throw new IllegalStateException("There isn't attribute with name: "+attributeName);
//      }
//
//      final AspectAttribute attribute = context.getLocalAttribute(attributeName);
//      HxCgenUtils.genericLoadSlot(attribute.getType(), attribute.getIndex(), codeStream);
//    }
//  }

  @Override
  public String toString() {
    return "AfterAspectStep{}";
  }
}
