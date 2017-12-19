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
public class BALOAD
  extends ArrayLoadInstruction {

  public BALOAD() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.BALOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.BALOAD();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public BALOAD clone() {
    return new BALOAD();
  }
}
