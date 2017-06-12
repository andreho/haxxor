package net.andreho.haxxor.cgen.instr.local;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IINC
    extends AbstractLocalAccessInstruction {

  protected final int increment;

  //----------------------------------------------------------------------------------------------------------------

  public IINC(int variable, int increment) {
    //variable: may be used with WIDE instruction
    super(Opcodes.IINC, variable);
    this.increment = increment; //may be used with WIDE instruction
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(HxComputingContext context, HxCodeStream codeStream) {
    codeStream.IINC(getLocalIndex(), this.increment);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.operand + " += " + this.increment + ")";
  }
}
