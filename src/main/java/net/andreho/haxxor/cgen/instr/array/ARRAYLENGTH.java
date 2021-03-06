package net.andreho.haxxor.cgen.instr.array;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ARRAYLENGTH
  extends ZeroOperandInstruction {

  public ARRAYLENGTH() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.ARRAYLENGTH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ARRAYLENGTH();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public ARRAYLENGTH clone() {
    return new ARRAYLENGTH();
  }
}
