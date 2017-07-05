package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractSimpleJumpInstruction
    extends AbstractJumpInstruction {

  public AbstractSimpleJumpInstruction(int opcode, LABEL label) {
    super(opcode, label);
  }

  @Override
  public LABEL getLabel() {
    return super.getLabel();
  }

  public abstract AbstractSimpleJumpInstruction clone(LABEL label);
}
