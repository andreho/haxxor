package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Swap the top two operand stack values.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class SWAP
    extends AbstractZeroOperandInstruction {

  public SWAP() {
    super(Opcodes.SWAP);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.SWAP();
  }

  @Override
  public List<Object> apply(final Context context) {
    Object value1 = context.getStack()
                           .peek();
    Object value2 = context.getStack()
                           .peek(1);

    return context.getStackPush()
                  .prepare()
                  .push(value1, value2)
                  .get();
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }
}