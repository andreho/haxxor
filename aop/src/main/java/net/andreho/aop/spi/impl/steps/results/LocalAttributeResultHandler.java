package net.andreho.aop.spi.impl.steps.results;

import net.andreho.aop.api.injectable.Attribute;
import net.andreho.aop.spi.AspectContext;
import net.andreho.aop.spi.AspectStep;
import net.andreho.aop.spi.AspectStepResultHandler;
import net.andreho.haxxor.cgen.HxCgenUtils;
import net.andreho.haxxor.cgen.HxInstruction;
import net.andreho.haxxor.cgen.HxInstructionSort;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;
import net.andreho.haxxor.spec.api.HxAnnotation;
import net.andreho.haxxor.spec.api.HxMethod;
import net.andreho.haxxor.spec.api.HxType;

import java.util.Optional;

/**
 * <br/>Created by a.hofmann on 21.06.2017 at 01:42.
 */
public class LocalAttributeResultHandler implements AspectStepResultHandler {
  private static final String ATTRIBUTE_ANNOTATION_TYPE_NAME = Attribute.class.getName();
  public static final LocalAttributeResultHandler INSTANCE = new LocalAttributeResultHandler();

  @Override
  public boolean handleReturnValue(final AspectStep<?> aspectStep,
                                   final AspectContext context,
                                   final HxMethod interceptor,
                                   final HxMethod method,
                                   final HxInstruction instruction) {
    final Optional<HxAnnotation> optional = interceptor.getAnnotation(ATTRIBUTE_ANNOTATION_TYPE_NAME);
    if(!optional.isPresent()) {
      return false;
    }

    final HxAnnotation attributeAnnotation = optional.get();
    final String attributeName = attributeAnnotation.getAttribute("value", "");

    if(attributeName.isEmpty()) {
      return false;
    }

    if(context.hasLocalAttribute(attributeName)) {
      throw new IllegalStateException("Local attribute with given name was already declared: "+attributeName);
    }

    final int slotOffset = method.getParametersSlots() + (method.isStatic()? 0 : 1);
    final HxType returnType = interceptor.getReturnType();
    final int variableSize = returnType.getSlotsCount();

    instruction.forEachNext(
      ins -> ins.has(HxInstructionSort.Load) || ins.has(HxInstructionSort.Store),
      ins -> shiftAccessToLocalVariables(ins, slotOffset, variableSize)
    );

    context.createLocalAttribute(attributeName, returnType, slotOffset);
    HxCgenUtils.genericStoreSlot(returnType, slotOffset, instruction.asStream());
    return true;
  }

  private void shiftAccessToLocalVariables(HxInstruction instruction, int parametersOffset, int offsetAddition) {
    final AbstractLocalAccessInstruction accessInstruction = (AbstractLocalAccessInstruction) instruction;
    int slotIndex = accessInstruction.getOperand();
    if(slotIndex >= parametersOffset) {
      accessInstruction.setOperand(slotIndex + offsetAddition);
    }
  }
}
