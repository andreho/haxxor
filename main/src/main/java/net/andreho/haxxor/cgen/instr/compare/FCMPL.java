package net.andreho.haxxor.cgen.instr.compare;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class FCMPL
    extends AbstractZeroOperandInstruction {

  public FCMPL() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Comparison.FCMPL;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.FCMPL();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_INT;
  }
}
