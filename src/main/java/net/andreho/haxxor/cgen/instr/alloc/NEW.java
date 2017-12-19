package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.TypedInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEW
  extends TypedInstruction {

  public NEW(String className) {
    super(className);
  }

  public String getType() {
    return getOperand();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.NEW;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NEW(getOperand());
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public NEW clone() {
    return new NEW(getType());
  }
}
