package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.SingleOperandInstruction;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class BIPUSH
  extends SingleOperandInstruction {

  public BIPUSH(int value) {
    this((byte) (0xFF & value));
  }

  public BIPUSH(byte value) {
    super(value);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Constants.BIPUSH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.BIPUSH((byte) this.operand);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  @Override
  public BIPUSH clone() {
    return new BIPUSH(getOperand());
  }
}
