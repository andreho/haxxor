package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class AbstractSwitchJumpInstruction
    extends AbstractJumpInstruction {

  protected final LABEL[] labels;

  public AbstractSwitchJumpInstruction(final int opcode,
                                       final LABEL defaultLabel,
                                       final LABEL[] labels) {
    super(opcode, defaultLabel);
    this.labels = labels;
  }

  public LABEL getDefaultLabel() {
    return getLabel();
  }

  public LABEL[] getLabels() {
    return labels;
  }

  public abstract AbstractSwitchJumpInstruction clone(LABEL defaultLabel, LABEL[] labels);
}
