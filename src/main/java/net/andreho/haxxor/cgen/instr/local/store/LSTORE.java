package net.andreho.haxxor.cgen.instr.local.store;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.LocalAccessInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class LSTORE
  extends LocalAccessInstruction {

  public LSTORE(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Store.LSTORE;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LSTORE(getLocalIndex());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public LSTORE clone() {
    return clone(getLocalIndex());
  }

  @Override
  public LSTORE clone(final int var) {
    return new LSTORE(var);
  }
}
