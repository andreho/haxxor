package net.andreho.haxxor.cgen.instr.jumps;

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
public class GOTO
  extends BasicJumpInstruction {

  public GOTO(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.GOTO;
  }

  @Override
  public GOTO clone() {
    return clone((LABEL) getLabel().clone());
  }

  @Override
  public GOTO clone(final LABEL label) {
    return new GOTO(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.GOTO(this.label);
  }
}
