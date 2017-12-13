package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

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
public class IF_ACMPEQ
  extends BasicJumpInstruction {

  public IF_ACMPEQ(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IF_ACMPEQ;
  }

  @Override
  public IF_ACMPEQ clone() {
    return clone((LABEL) getLabel().clone());
  }

  @Override
  public IF_ACMPEQ clone(final LABEL label) {
    return new IF_ACMPEQ(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ACMPEQ(this.label);
  }
}
