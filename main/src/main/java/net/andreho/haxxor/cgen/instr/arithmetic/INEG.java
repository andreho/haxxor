package net.andreho.haxxor.cgen.instr.arithmetic;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class INEG
    extends AbstractZeroOperandInstruction {

  public INEG() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Arithmetic.INEG;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.INEG();
  }

  @Override
  public List<Object> compute(HxComputingContext context) {
    return PUSH_INT;
  }
}
