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
public class MULTIANEWARRAY
  extends TypedInstruction {

  private final int dimensions;

  public MULTIANEWARRAY(String className,
                        int dims) {
    super(className);
    this.dimensions = dims;
  }

  public String getArrayType() {
    return getOperand();
  }

  public int getDimensions() {
    return dimensions;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Allocation.MULTIANEWARRAY;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.MULTIANEWARRAY(getOperand(), this.dimensions);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public int getStackPopSize() {
    return this.dimensions;
  }

  @Override
  public MULTIANEWARRAY clone() {
    return new MULTIANEWARRAY(getArrayType(), getDimensions());
  }

  @Override
  public String toString() {
    return "MULTIANEWARRAY (" + getOperand() + ", " + dimensions + ")";
  }
}
