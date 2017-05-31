package net.andreho.haxxor.cgen.instr.local;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
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
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.IINC(getLocalIndex(), this.increment);
  }

  @Override
  public List<Object> apply(final Context context) {
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.operand + " += " + this.increment + ")";
  }
}
