package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP
    extends AbstractZeroOperandInstruction {

  public DUP() {
    super(Opcodes.DUP);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DUP();
  }

  @Override
  public List<Object> apply(final Context context) {
    Object value = context.getStack()
                          .peek();
    return context.getStackPush()
                  .prepare()
                  .push(value, value)
                  .get();
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }
}
