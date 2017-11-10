package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxArrayType;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.SingleOperandInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEWARRAY
  extends SingleOperandInstruction {

  public NEWARRAY(HxArrayType type) {
    super(type.getCode());
  }

  public NEWARRAY(int operand) {
    super(operand);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.NEWARRAY;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NEWARRAY(HxArrayType.fromCode(this.operand));
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public String toString() {
    return "NEWARRAY (" + HxArrayType.fromCode(this.operand).getClassName() + ")";
  }
}
