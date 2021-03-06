package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DCONST_0
  extends ZeroOperandInstruction {

  public DCONST_0() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Constants.DCONST_0;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DCONST_0();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public DCONST_0 clone() {
    return new DCONST_0();
  }
}
