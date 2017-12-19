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
public class IRETURN
  extends ExitInstruction {

  public IRETURN() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Exit.IRETURN;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IRETURN();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public IRETURN clone() {
    return new IRETURN();
  }
}
