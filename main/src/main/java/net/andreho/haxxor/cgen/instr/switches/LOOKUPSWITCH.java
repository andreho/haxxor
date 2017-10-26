package net.andreho.haxxor.cgen.instr.switches;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputationContext;
import net.andreho.haxxor.cgen.HxFrame;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSwitchJumpInstruction;
import net.andreho.haxxor.cgen.instr.misc.LABEL;

import java.util.Arrays;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class LOOKUPSWITCH
  extends AbstractSwitchJumpInstruction {

  private final int[] keys;

  public LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels) {
    super(defaultLabel, labels);
    this.keys = keys;
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Switches.LOOKUPSWITCH;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LOOKUPSWITCH(this.label, this.keys, this.labels);
  }

  @Override
  public void compute(final HxComputationContext context, final HxFrame frame) {
    context.getExecutor().visit(context, this, frame);
  }

  public int[] getKeys() {
    return this.keys;
  }

  @Override
  public LOOKUPSWITCH clone(final LABEL defaultLabel, final LABEL[] labels) {
    return new LOOKUPSWITCH(defaultLabel, getKeys(), labels);
  }

  @Override
  public String toString() {
    return super.toString() + " (" + Arrays.toString(this.keys) + ", " + Arrays.toString(this.labels) + ", " +
           this.label + ")";
  }
}
