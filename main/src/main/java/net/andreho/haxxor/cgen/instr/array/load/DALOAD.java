package net.andreho.haxxor.cgen.instr.array.load;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayLoadInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DALOAD
    extends AbstractArrayLoadInstruction {

  public DALOAD() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.DALOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DALOAD();
  }

  @Override
  protected void checkArrayType(final Object arrayType) {
    super.checkArrayType(arrayType);
    if (!"[D".equals(arrayType)) {
      throw new IllegalArgumentException("Expected an array of current type: double[]");
    }
  }
}
