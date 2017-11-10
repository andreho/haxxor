package net.andreho.haxxor.cgen.instr.subroutine;

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
public class JSR
  extends SimpleJumpInstruction {

  public JSR(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.SubRoutine.JSR;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.JSR(this.label);
  }

  @Override
  public JSR clone(final LABEL label) {
    return new JSR(label);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }
}
