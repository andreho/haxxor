package net.andreho.haxxor.cgen.instr.switches;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.SwitchJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class TABLESWITCH
  extends SwitchJumpInstruction {

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
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  public int getMin() {
    return this.min;
  }

  public int getMax() {
    return this.max;
  }

  @Override
  public TABLESWITCH clone() {
    return clone(getMin(), getMax(), getDefaultLabel().clone(), cloneLabels(labels));
  }

  @Override
  public TABLESWITCH clone(LABEL defaultLabel, LABEL[] labels) {
    return clone(getMin(), getMax(), defaultLabel, labels);
  }

  public TABLESWITCH clone(int min, int max, LABEL defaultLabel, LABEL[] labels) {
    return new TABLESWITCH(min, max, defaultLabel, labels);
  }

  @Override
  protected String print() {
    return getInstructionName() + " (" + this.min + ", " + this.max + ", " + Arrays.toString(this.labels) + ", " + this.label + ")";
  }
}
