package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.BasicJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * IFGE succeeds if and only if value >= 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFGE
  extends BasicJumpInstruction {

  public IFGE(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IFGE;
  }

  @Override
  public IFGE clone() {
    return clone((LABEL) getLabel().clone());
  }

  @Override
  public IFGE clone(final LABEL label) {
    return new IFGE(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFGE(this.label);
  }
}
