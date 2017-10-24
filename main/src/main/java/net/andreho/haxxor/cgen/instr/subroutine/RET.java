package net.andreho.haxxor.cgen.instr.subroutine;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSingleOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class RET
    extends AbstractSingleOperandInstruction {

  public RET(int var) {
    super(var);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.SubRoutine.RET;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.RET(this.operand);
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}