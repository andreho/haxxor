package net.andreho.haxxor.cgen.instr.bitwise;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IUSHR
  extends ZeroOperandInstruction {

  public IUSHR() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.BitWise.IUSHR;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IUSHR();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public IUSHR clone() {
    return new IUSHR();
  }
}
