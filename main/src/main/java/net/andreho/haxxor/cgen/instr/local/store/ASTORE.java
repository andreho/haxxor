package net.andreho.haxxor.cgen.instr.local.store;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ASTORE
    extends AbstractLocalAccessInstruction {

  public ASTORE(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Store.ASTORE;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ASTORE(getLocalIndex());
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    Object operand = context.getStack().pop();

    if (operand instanceof Integer) {
      throw new IllegalArgumentException(
          "A object reference is expected at slot's index [" + getLocalIndex() + "], but got: " + operand);
    }
    context.getLocals().set(getLocalIndex(), operand);
    return NO_STACK_PUSH;
  }
}
