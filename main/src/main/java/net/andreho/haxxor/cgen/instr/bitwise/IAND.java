package net.andreho.haxxor.cgen.instr.bitwise;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IAND
    extends AbstractZeroOperandInstruction {

  public IAND() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.BitWise.IAND;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IAND();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_INT;
  }
}
