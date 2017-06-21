package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStepParameterInjector;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class BeforeAspectStep
  extends AbstractCallingAspectStep<HxMethod> {

  public BeforeAspectStep(final int index,
                          final String profileName,
                          final AspectStepType type,
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

    System.out.println("@Before[" + getIndex() + "]: " + method);

    final HxCode code = method.getCode();
    final HxMethod interceptor = getInterceptor();

    final HxInstruction begin = code.getFirst();
    final HxInstruction anchor = begin.getNext();

    handleInterceptorParameters(ctx, interceptor, method, begin);

    anchor.getPrevious().asStream().INVOKESTATIC(interceptor);

    handleReturnValue(ctx, interceptor, method, anchor.getPrevious());

    return true;
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

//
//  private void expandAccessToLocalVariables(HxInstruction instruction, int parametersOffset, int offsetAddition) {
//    AbstractLocalAccessInstruction accessInstruction = (AbstractLocalAccessInstruction) instruction;
//    int slotIndex = accessInstruction.getOperand();
//    if(slotIndex >= parametersOffset) {
//      accessInstruction.setOperand(slotIndex + offsetAddition);
//    }
//  }
//  private boolean storeAttribute(final AspectContext context,
//                                 final HxMethod interceptor,
//                                 final HxMethod intercepted,
//                                 final HxInstruction instruction,
//                                 final HxType returnType) {
//    if(interceptor.isAnnotationPresent(Attribute.class)) {
//      final HxAnnotation attributeAnnotation = interceptor.getAnnotation(Attribute.class).get();
//      final String attributeName = attributeAnnotation.getAttribute("value", "");
//      final boolean inheritable = attributeAnnotation.getAttribute("inheritable", false);
//
//      if(attributeName.isEmpty()) {
//        return false;
//      }
//
//      if(context.hasLocalAttribute(attributeName)) {
//        System.out.println("Local-Attribute was already declared: "+attributeName);
//        return false;
//      }
//
//      final int slotOffset = intercepted.getParametersSlots() + (intercepted.isStatic()? 0 : 1);
//      final int variableSize = returnType.getSlotsCount();
//      instruction.forEachNext(
//        ins -> ins.has(HxInstructionSort.Load) || ins.has(HxInstructionSort.Store),
//        ins -> expandAccessToLocalVariables(ins, slotOffset, variableSize)
//      );
//
//      context.createLocalAttribute(attributeName, returnType, slotOffset);
//      HxCgenUtils.genericStoreSlot(returnType, slotOffset, instruction.asStream());
//
//      return true;
//    }
//    return false;
//  }
//
//  private void injectArguments(final AspectContext context,
//                               final HxParameter parameter,
//                               final HxMethod intercepted,
//                               final HxExtendedCodeStream codeStream) {
//    HxCgenUtils.packArguments(intercepted.getParameterTypes(), intercepted.isStatic() ? 0 : 1, codeStream);
//  }
//
//  private void injectArgument(final AspectContext context,
//                              final HxParameter parameter,
//                              final HxMethod intercepted,
//                              final HxExtendedCodeStream codeStream,
//                              final HxAnnotation argAnnotation) {
//
//    HxCgenUtils.packArguments(intercepted.getParameterTypes(), intercepted.isStatic() ? 0 : 1, codeStream);
//  }
//
//  private void injectArity(final AspectContext context,
//                           final HxParameter parameter,
//                           final HxMethod intercepted,
//                           final HxExtendedCodeStream codeStream) {
//    codeStream.LDC(intercepted.getParametersCount());
//  }
//
//  private void injectDeclaring(final AspectContext context,
//                          final HxParameter parameter,
//                          final HxMethod intercepted,
//                          final HxExtendedCodeStream codeStream) {
//    codeStream.TYPE(intercepted.getDeclaringMember());
//  }
//
//  private void injectThis(final AspectContext context,
//                          final HxParameter parameter,
//                          final HxMethod intercepted,
//                          final HxExtendedCodeStream codeStream) {
//    if (intercepted.isStatic()) {
//      codeStream.ACONST_NULL();
//    } else {
//      codeStream.THIS();
//    }
//  }
//
//  private void injectLine(final AspectContext context,
//                          final HxParameter parameter,
//                          final HxMethod intercepted,
//                          final HxExtendedCodeStream codeStream) {
//
//    Optional<HxInstruction> lineNumber = intercepted.getCode().getFirst()
//                                               .findFirst(i -> i instanceof LINE_NUMBER);
//    if(lineNumber.isPresent()) {
//      LINE_NUMBER ln = (LINE_NUMBER) lineNumber.get();
//      codeStream.LDC(ln.getLine());
//    } else {
//      codeStream.LDC(-1);
//    }
//  }

  @Override
  public String toString() {
    return "BeforeAspectStep{}";
  }
}
