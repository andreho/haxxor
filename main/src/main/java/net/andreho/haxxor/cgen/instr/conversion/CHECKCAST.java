package net.andreho.haxxor.cgen.instr.conversion;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.StringOperandInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class CHECKCAST
  extends StringOperandInstruction {

  public CHECKCAST(String internalType) {
    super(internalType);
  }

  public String getType() {
    return getOperand();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.CHECKCAST;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.CHECKCAST(getOperand());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }
}
