package net.andreho.haxxor.cgen.instr.abstr;

import net.andreho.haxxor.cgen.instr.misc.LABEL;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public abstract class SwitchJumpInstruction
  extends JumpInstruction {

  protected final LABEL[] labels;

  public SwitchJumpInstruction(final LABEL defaultLabel,
                               final LABEL[] labels) {
    super(defaultLabel);
    this.labels = labels;

    for (LABEL label : labels) {
      label.addReference(this);
    }
  }

  public LABEL getDefaultLabel() {
    return getLabel();
  }

  public LABEL[] getLabels() {
    return labels;
  }

  public abstract SwitchJumpInstruction clone(LABEL defaultLabel, LABEL[] labels);
}
