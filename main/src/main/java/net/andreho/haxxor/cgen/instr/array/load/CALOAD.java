package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class CALOAD
    extends AbstractArrayLoadInstruction {

  public CALOAD() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.CALOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.CALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[C".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: char[]");
    }
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }
}
