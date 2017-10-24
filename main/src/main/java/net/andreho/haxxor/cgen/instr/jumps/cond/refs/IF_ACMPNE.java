package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ACMPNE
  extends AbstractSimpleJumpInstruction {

  public IF_ACMPNE(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IF_ACMPNE;
  }

  @Override
  public IF_ACMPNE clone(final LABEL label) {
    return new IF_ACMPNE(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ACMPNE(this.label);
  }
}
