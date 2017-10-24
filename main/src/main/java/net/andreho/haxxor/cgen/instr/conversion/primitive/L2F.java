package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class L2F
    extends AbstractZeroOperandInstruction {

  public L2F() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.L2F;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.L2F();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_FLOAT;
  }
}
