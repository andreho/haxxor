package net.andreho.haxxor.cgen.instr.jumps;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class GOTO
  extends AbstractSimpleJumpInstruction {

  public GOTO(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.GOTO;
  }

  @Override
  public GOTO clone(final LABEL label) {
    return new GOTO(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.GOTO(this.label);
  }
}
