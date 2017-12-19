package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class SALOAD
  extends ArrayLoadInstruction {

  public SALOAD() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.SALOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SALOAD();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public SALOAD clone() {
    return new SALOAD();
  }
}
