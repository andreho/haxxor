package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ANEWARRAY
    extends AbstractStringOperandInstruction {

  public ANEWARRAY(String className) {
    super(Opcodes.ANEWARRAY, className);
  }

  //----------------------------------------------------------------------------------------------------------------

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.ANEWARRAY(getOperand());
  }

  @Override
  public List<Object> apply(final Context context) {
    return context.getStackPush()
                  .prepare()
                  .push("[" + getOperand())
                  .get();
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }

  @Override
  public String toString() {
    return super.toString() + " " + getOperand();
  }
}
