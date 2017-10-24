package net.andreho.haxxor.cgen.instr.sync;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class MONITORENTER
    extends AbstractZeroOperandInstruction {

  public MONITORENTER() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Synchronization.MONITORENTER;
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.MONITORENTER();
  }
}
