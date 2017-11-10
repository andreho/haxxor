package net.andreho.haxxor.cgen.instr.array.store;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractArrayStoreInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class AASTORE
  extends AbstractArrayStoreInstruction {

  public AASTORE() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.AASTORE;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.AASTORE();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

//  @Override
//  protected void checkArrayType(final Object arrayType, final int depth) {
//    super.checkArrayType(arrayType, depth);
//    String array = arrayType.toString();
//
//    if (array.length() == 2) {
//      switch (array.charAt(1)) {
//        case 'Z':
//        case 'B':
//        case 'S':
//        case 'C':
//        case 'I':
//        case 'F':
//        case 'J':
//        case 'D':
//          throw new IllegalArgumentException("Expected an reference array type, but got: " + arrayType);
//      }
//    }
//  }
}
