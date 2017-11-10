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
public class AALOAD
  extends ArrayLoadInstruction {

  public AALOAD() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.AALOAD;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.AALOAD();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

//  @Override
//  protected void checkArrayType(final Object arrayType) {
//    super.checkArrayType(arrayType);
//    final String array = arrayType.toString();
//
//    if (array.length() == 2) {
//      final char c = array.charAt(1);
//      if(c != 'L' && c != '[') {
//        throw new IllegalArgumentException("Expected an reference array type, but got: " + arrayType);
//      }
//    }
//  }
}
