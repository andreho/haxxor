package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.BasicJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPLE
  extends BasicJumpInstruction {

  public IF_ICMPLE(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IF_ICMPLE;
  }

  @Override
  public IF_ICMPLE clone() {
    return clone((LABEL) getLabel().clone());
  }

  @Override
  public IF_ICMPLE clone(final LABEL label) {
    return new IF_ICMPLE(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ICMPLE(this.label);
  }
}
