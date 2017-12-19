package net.andreho.haxxor.cgen.instr.subroutine;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.SingleOperandInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class RET
  extends SingleOperandInstruction {

  public RET(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.SubRoutine.RET;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.RET(this.operand);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public RET clone() {
    return new RET(getOperand());
  }
}
