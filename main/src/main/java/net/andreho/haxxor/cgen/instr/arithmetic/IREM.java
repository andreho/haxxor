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
public class IREM
    extends AbstractZeroOperandInstruction {

  public IREM() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Arithmetic.IREM;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IREM();
  }

  @Override
  public List<Object> compute(HxComputingContext context) {
    return PUSH_INT;
  }
}
