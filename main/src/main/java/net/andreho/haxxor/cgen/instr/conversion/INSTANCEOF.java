package net.andreho.haxxor.cgen.instr.conversion;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class INSTANCEOF
    extends AbstractStringOperandInstruction {

  public INSTANCEOF(String operand) {
    super(operand);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.INSTANCEOF;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INSTANCEOF(getOperand());
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_INT;
  }
}
