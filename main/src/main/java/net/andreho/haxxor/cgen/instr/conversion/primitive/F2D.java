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
public class F2D
    extends AbstractZeroOperandInstruction {

  public F2D() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Conversion.F2D;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.F2D();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_DOUBLE;
  }
}
