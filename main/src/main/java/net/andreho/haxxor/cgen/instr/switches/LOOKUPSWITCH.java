package net.andreho.haxxor.cgen.instr.switches;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class LOOKUPSWITCH
    extends AbstractJumpInstruction {

  private final int[] keys;
  private final LABEL[] labels;

  public LOOKUPSWITCH(LABEL defaultLabel, int[] keys, LABEL[] labels) {
    super(Opcodes.LOOKUPSWITCH, defaultLabel);
    this.keys = keys;
    this.labels = labels;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LOOKUPSWITCH(this.label, this.keys, this.labels);
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

  public int[] getKeys() {
    return this.keys;
  }

  public LABEL[] getLabels() {
    return this.labels;
  }

  public LABEL getDefaultLabel() {
    return this.label;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + Arrays.toString(this.keys) + ", " + Arrays.toString(this.labels) + ", " +
           this.label + ")";
  }
}
