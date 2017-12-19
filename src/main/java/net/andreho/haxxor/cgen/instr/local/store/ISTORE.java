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
public class ISTORE
  extends LocalAccessInstruction {

  public ISTORE(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Store.ISTORE;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ISTORE(getLocalIndex());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public ISTORE clone() {
    return clone(getLocalIndex());
  }

  @Override
  public ISTORE clone(final int var) {
    return new ISTORE(var);
  }
}
