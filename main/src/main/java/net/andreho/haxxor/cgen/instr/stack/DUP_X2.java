package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * Duplicate the top operand stack value and insert two or three values down.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP_X2
  extends ZeroOperandInstruction {

  public DUP_X2() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Stack.DUP_X2;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DUP_X2();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public DUP_X2 clone() {
    return new DUP_X2();
  }
}
