package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class L2F
  extends ZeroOperandInstruction {

  public L2F() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.L2F;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.L2F();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public L2F clone() {
    return new L2F();
  }
}
