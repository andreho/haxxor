package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class SimpleJumpInstruction
  extends JumpInstruction {

  public SimpleJumpInstruction(LABEL label) {
    super(label);
  }

  @Override
  public LABEL getLabel() {
    return super.getLabel();
  }

  public abstract SimpleJumpInstruction clone(LABEL label);
}
