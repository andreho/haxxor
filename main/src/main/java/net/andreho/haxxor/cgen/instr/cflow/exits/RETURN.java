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
public class RETURN
  extends ExitInstruction {

  public RETURN() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Exit.RETURN;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.RETURN();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public RETURN clone() {
    return new RETURN();
  }
}
