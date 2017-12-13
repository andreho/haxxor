package net.andreho.haxxor.cgen.instr.cflow.exits;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ExitInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ARETURN
  extends ExitInstruction {

  public ARETURN() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Exit.ARETURN;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ARETURN();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public ARETURN clone() {
    return new ARETURN();
  }
}
