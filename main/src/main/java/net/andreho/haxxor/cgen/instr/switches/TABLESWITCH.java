package net.andreho.haxxor.cgen.instr.switches;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class TABLESWITCH
    extends AbstractJumpInstruction {

  protected final int min;
  protected final int max;
  protected final LABEL[] labels;

  public TABLESWITCH(int min, int max, LABEL defaultLabel, LABEL... labels) {
    super(Opcodes.TABLESWITCH, defaultLabel);
    this.min = min;
    this.max = max;
    this.labels = labels;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.TABLESWITCH(this.min, this.max, this.label, this.labels);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
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

  public LABEL[] getLabels() {
    return this.labels;
  }

  public LABEL getDefaultLabel() {
    return this.label;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.min + ", " + this.max + ", " + this.labels + ", " + this.label + ")";
  }
}
