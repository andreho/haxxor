package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.BasicJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * IFEQ succeeds if and only if value = 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFEQ
  extends BasicJumpInstruction {

  public IFEQ(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IFEQ;
  }

  @Override
  public IFEQ clone() {
    return clone((LABEL) getLabel().clone());
  }

  @Override
  public IFEQ clone(final LABEL label) {
    return new IFEQ(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFEQ(this.label);
  }
}
