package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.StringOperandInstruction;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MULTIANEWARRAY
  extends StringOperandInstruction {

  private final int dimension;

  public MULTIANEWARRAY(String className,
                        int dims) {
    super(className);
    this.dimension = dims;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.MULTIANEWARRAY;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.MULTIANEWARRAY(getOperand(), this.dimension);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public int getStackPopSize() {
    return this.dimension;
  }

  @Override
  public String toString() {
    return "MULTIANEWARRAY ("+getOperand()+", "+dimension+")";
  }

  private static String multiply(String str,
                                 int times) {
    final StringBuilder builder = new StringBuilder();
    while (times-- > 0) {
      builder.append(str);
    }
    return builder.toString();
  }
}
