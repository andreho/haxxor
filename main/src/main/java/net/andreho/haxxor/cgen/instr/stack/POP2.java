package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * Pop the top one or two operand stack values.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class POP2
  extends ZeroOperandInstruction {

  public POP2() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Stack.POP2;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.POP2();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public POP2 clone() {
    return new POP2();
  }
}
