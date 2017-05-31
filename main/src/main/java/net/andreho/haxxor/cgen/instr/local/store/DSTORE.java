package net.andreho.haxxor.cgen.instr.local.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DSTORE
    extends AbstractLocalAccessInstruction {

  public DSTORE(int var) {
    super(Opcodes.DSTORE, var);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DSTORE(getLocalIndex());
  }

  @Override
  public List<Object> apply(final Context context) {
    Object operand = context.getStack()
                            .peek();
    if (operand != Opcodes.DOUBLE) {
      throw new IllegalArgumentException(
          "A double operand is expected at slot index [" + getLocalIndex() + "], but got: " + operand);
    }
    context.getLocals()
           .set(getLocalIndex(), operand);
    return NO_STACK_PUSH;
  }

  @Override
  public int getStackPopCount() {
    return 2;
  }
}
