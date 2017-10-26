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
public class SASTORE
    extends AbstractArrayStoreInstruction {

  public SASTORE() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Array.SASTORE;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.SASTORE();
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

//  @Override
//  protected void checkArrayType(final Object arrayType, final int depth) {
//    super.checkArrayType(arrayType, depth);
//    if (!"[S".equals(arrayType)) {
//      throw new IllegalArgumentException("Expected an short[] array type, but got: " + arrayType);
//    }
//  }
}
