package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class LCONST_1
    extends AbstractZeroOperandInstruction {

  public LCONST_1() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Constants.LCONST_1;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LCONST_1();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_LONG;
  }
}
