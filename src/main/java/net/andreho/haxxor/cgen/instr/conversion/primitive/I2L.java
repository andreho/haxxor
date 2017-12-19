package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.ZeroOperandInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class I2L
  extends ZeroOperandInstruction {

  public I2L() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.I2L;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.I2L();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public I2L clone() {
    return new I2L();
  }
}
