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
public class DLOAD
  extends LocalAccessInstruction {

  public DLOAD(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Load.DLOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DLOAD(getLocalIndex());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public DLOAD clone(final int var) {
    return new DLOAD(var);
  }
}
