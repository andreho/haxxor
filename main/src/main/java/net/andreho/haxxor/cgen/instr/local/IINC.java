package net.andreho.haxxor.cgen.instr.local;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IINC
    extends AbstractLocalAccessInstruction {
  protected int increment;

  public IINC(int variable, int increment) {
    super(variable);
    this.increment = increment; //may be used with WIDE instruction
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Store.IINC;
  }

  public int getIncrement() {
    return increment;
  }

  public void setIncrement(final int increment) {
    this.increment = increment;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IINC(getLocalIndex(), this.increment);
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.operand + " += " + this.increment + ")";
  }
}
