package net.andreho.haxxor.cgen.instr.cflow;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ATHROW
    extends AbstractZeroOperandInstruction {

  public ATHROW() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Exit.ATHROW;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ATHROW();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
