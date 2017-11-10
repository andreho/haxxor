package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.StringOperandInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ANEWARRAY
  extends StringOperandInstruction {

  public ANEWARRAY(String className) {
    super(className);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.ANEWARRAY;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ANEWARRAY(getOperand());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }
}
