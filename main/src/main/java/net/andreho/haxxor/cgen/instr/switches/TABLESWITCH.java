package net.andreho.haxxor.cgen.instr.switches;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSwitchJumpInstruction;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class TABLESWITCH
  extends AbstractSwitchJumpInstruction {

  protected final int min;
  protected final int max;

  public TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    super(defaultLabel, labels);
    this.min = min;
    this.max = max;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Switches.TABLESWITCH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.TABLESWITCH(this.min, this.max, this.label, this.labels);
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    for (LABEL label : getLabels()) {
      label.addReference(this);
    }

    this.getDefaultLabel()
        .addReference(this);
    return NO_STACK_PUSH;
  }


  public int getMin() {
    return this.min;
  }

  public int getMax() {
    return this.max;
  }

  @Override
  public TABLESWITCH clone(LABEL defaultLabel, LABEL[] labels) {
    return new TABLESWITCH(getMin(), getMax(), defaultLabel, labels);
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.min + ", " + this.max + ", " + Arrays.toString(this.labels) + ", " + this.label + ")";
  }
}
