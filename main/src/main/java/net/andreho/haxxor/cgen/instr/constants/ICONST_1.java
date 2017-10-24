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
public class ICONST_1
    extends AbstractZeroOperandInstruction {

  public ICONST_1() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Constants.ICONST_1;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ICONST_1();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_INT;
  }
}
