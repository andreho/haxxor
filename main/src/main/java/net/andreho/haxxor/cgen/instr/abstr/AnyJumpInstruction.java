package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AnyJumpInstruction
    extends AbstractInstruction {

  protected final LABEL label;

  public AnyJumpInstruction(LABEL label) {
    super();
    this.label = label;
    label.addReference(this);
  }

  protected LABEL getLabel() {
    return label;
  }

  @Override
  protected String print() {
    return getInstructionName() + " -> " + this.label.toFormattedString();
  }
}
