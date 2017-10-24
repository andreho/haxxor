package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEW
    extends AbstractStringOperandInstruction {

  public NEW(String className) {
    super(className);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.NEW;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NEW(getOperand());
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_UNINITIALIZED_THIS;
  }
}
