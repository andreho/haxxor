package net.andreho.aop.spi.impl.steps;

import net.andreho.aop.api.DefineVariable;
import net.andreho.aop.api.injectable.Args;
import net.andreho.aop.api.injectable.Arity;
import net.andreho.aop.api.injectable.Declaring;
import net.andreho.aop.api.injectable.Line;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.spi.AspectApplicationContext;
import net.andreho.aop.spi.AspectDefinition;
import net.andreho.aop.spi.AspectStepType;
import net.andreho.aop.spi.ElementMatcher;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxExtendedCodeStream;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionFactory;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.impl.InstructionCodeStream;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;
import net.andreho.haxxor.cgen.instr.misc.LINE_NUMBER;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxCode;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxParameter;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 19.06.2017 at 00:34.
 */
public class BeforeAspectStep
  extends AbstractCallingAspectStep<HxMethod> {

  public BeforeAspectStep(final int index,
                          final AspectStepType type,
                          final ElementMatcher<HxMethod> elementMatcher,
                          final String profileName,
                          final HxMethod interceptor) {
    super(index, type, elementMatcher, profileName, interceptor);
  }

  @Override
  public boolean needsAspectFactory() {
    return !getInterceptor().isStatic();
  }

  @Override
  public boolean apply(final AspectDefinition def,
                       final AspectApplicationContext ctx,
                       final HxMethod intercepted) {
    if (!match(intercepted) || !intercepted.hasCode()) {
      return false;
    }

    System.out.println("@Before[" + getIndex() + "]: " + intercepted);

    final HxCode code = intercepted.getCode();
    final HxMethod interceptor = getInterceptor();
    final HxInstructionFactory instructionFactory = code.getInstructionFactory();
    final InstructionCodeStream codeStream = new InstructionCodeStream<>(code.getFirst(), instructionFactory);

    handleInjectableParameters(def, interceptor, intercepted, codeStream);

    codeStream.INVOKESTATIC(interceptor);

    handleReturnValue(def, interceptor, intercepted, codeStream);

    return true;
  }

  private void handleReturnValue(final AspectDefinition def,
                                 final HxMethod interceptor,
                                 final HxMethod intercepted,
                                 final InstructionCodeStream codeStream) {

    final HxType returnType = interceptor.getReturnType();
    if(!interceptor.isAnnotationPresent(DefineVariable.class)) {
      switch (returnType.getSlotsCount()) {
        case 1:
          codeStream.POP();
          break;
        case 2:
          codeStream.POP2();
          break;
      }
    } else {
      final int slotOffset = intercepted.getParametersSlots() + (intercepted.isStatic()? 0 : 1);
      final int variableSize = returnType.getSlotsCount();
      codeStream.getCurrent().forEachNext(
        ins -> ins.hasInstructionSort(HxInstructionSort.Load) || ins.hasInstructionSort(HxInstructionSort.Store),
        ins -> expandAccessToLocalVariables(ins, slotOffset, variableSize)
      );
      HxCgenUtils.storeSlot(returnType, slotOffset, codeStream);
    }
  }

  private void expandAccessToLocalVariables(HxInstruction instruction, int parametersOffset, int offsetAddition) {
    AbstractLocalAccessInstruction accessInstruction = (AbstractLocalAccessInstruction) instruction;
    int slotIndex = accessInstruction.getOperand();
    if(slotIndex > parametersOffset) {
      accessInstruction.setOperand(slotIndex + offsetAddition);
    }
  }

  private void handleInjectableParameters(final AspectDefinition def,
                                          final HxMethod interceptor,
                                          final HxMethod intercepted,
                                          final HxExtendedCodeStream codeStream) {

    for (HxParameter parameter : interceptor.getParameters()) {
//      if (parameter.isAnnotationPresent(Arg.class)) {
//        injectArgument(def, intercepted, codeStream, parameter.getAnnotation(Arg.class).get());
//      } else
      if (parameter.isAnnotationPresent(Args.class)) {
        injectArguments(def, parameter, intercepted, codeStream);
      } else if (parameter.isAnnotationPresent(Arity.class)) {
        injectArity(def, parameter, intercepted, codeStream);
      } else if (parameter.isAnnotationPresent(This.class)) {
        injectThis(def, parameter, intercepted, codeStream);
      } else if (parameter.isAnnotationPresent(Line.class)) {
        injectLine(def, parameter, intercepted, codeStream);
      } else if (parameter.isAnnotationPresent(Declaring.class)) {
        injectDeclaring(def, parameter, intercepted, codeStream);
      }
    }
  }

  private void injectArguments(final AspectDefinition def,
                               final HxParameter parameter,
                               final HxMethod intercepted,
                               final HxExtendedCodeStream codeStream) {
    HxCgenUtils.packArguments(intercepted.getParameterTypes(), intercepted.isStatic() ? 0 : 1, codeStream);
  }

  private void injectArgument(final AspectDefinition def,
                              final HxParameter parameter,
                              final HxMethod intercepted,
                              final HxExtendedCodeStream codeStream,
                              final HxAnnotation argAnnotation) {

    HxCgenUtils.packArguments(intercepted.getParameterTypes(), intercepted.isStatic() ? 0 : 1, codeStream);
  }

  private void injectArity(final AspectDefinition def,
                           final HxParameter parameter,
                           final HxMethod intercepted,
                           final HxExtendedCodeStream codeStream) {
    codeStream.LDC(intercepted.getParametersCount());
  }

  private void injectDeclaring(final AspectDefinition def,
                          final HxParameter parameter,
                          final HxMethod intercepted,
                          final HxExtendedCodeStream codeStream) {
    codeStream.TYPE(intercepted.getDeclaringMember());
  }

  private void injectThis(final AspectDefinition def,
                          final HxParameter parameter,
                          final HxMethod intercepted,
                          final HxExtendedCodeStream codeStream) {
    if (intercepted.isStatic()) {
      codeStream.ACONST_NULL();
    } else {
      codeStream.THIS();
    }
  }

  private void injectLine(final AspectDefinition def,
                          final HxParameter parameter,
                          final HxMethod intercepted,
                          final HxExtendedCodeStream codeStream) {

    Optional<HxInstruction> lineNumber = intercepted.getCode().getFirst()
                                               .findFirst(i -> i instanceof LINE_NUMBER);
    if(lineNumber.isPresent()) {
      LINE_NUMBER ln = (LINE_NUMBER) lineNumber.get();
      codeStream.LDC(ln.getLine());
    } else {
      codeStream.LDC(-1);
    }
  }

  @Override
  public String toString() {
    return "BeforeAspectStep{}";
  }
}
