package net.andreho.haxxor.cgen.instr.alloc;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MULTIANEWARRAY
    extends AbstractStringOperandInstruction {

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
  public List<Object> compute(final HxComputingContext context) {
    return context.getStackPush()
                  .prepare()
                  .push(multiply(getOperand(), this.dimension))
                  .get();
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
    StringBuilder builder = new StringBuilder();
    while (times-- > 0) {
      builder.append(str);
    }
    return builder.toString();
  }
}
