package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * Duplicate the top one or two operand stack values and insert two, three, or four values down. <br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP2_X2
  extends ZeroOperandInstruction {

  public DUP2_X2() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Stack.DUP2_X2;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DUP2_X2();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }
}
