package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractSimpleJumpInstruction
    extends AbstractJumpInstruction {

  public AbstractSimpleJumpInstruction(LABEL label) {
    super(label);
  }

  @Override
  public LABEL getLabel() {
    return super.getLabel();
  }

  public abstract AbstractSimpleJumpInstruction clone(LABEL label);
}
