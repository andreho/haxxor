package net.andreho.haxxor.cgen.instr.local.load;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.LocalAccessInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ILOAD
  extends LocalAccessInstruction {

  public ILOAD(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Load.ILOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ILOAD(getLocalIndex());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public ILOAD clone(final int var) {
    return new ILOAD(var);
  }
}
