package net.andreho.haxxor.cgen.instr.cflow.exits;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class FRETURN
    extends AbstractInstruction {

  public FRETURN() {
    super();
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Exit.FRETURN;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.FRETURN();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
