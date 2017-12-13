package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * Swap the top two operand stack values.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class SWAP
  extends ZeroOperandInstruction {

  public SWAP() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Stack.SWAP;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SWAP();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public SWAP clone() {
    return new SWAP();
  }
}
