package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.SimpleJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPEQ
  extends SimpleJumpInstruction {

  public IF_ICMPEQ(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IF_ICMPEQ;
  }

  @Override
  public IF_ICMPEQ clone(final LABEL label) {
    return new IF_ICMPEQ(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ICMPEQ(this.label);
  }
}
