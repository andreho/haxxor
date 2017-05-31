package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Duplicate the top one or two operand stack values and insert two
 * or three values down.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DUP2_X1
    extends AbstractZeroOperandInstruction {

  public DUP2_X1() {
    super(Opcodes.DUP2_X1);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DUP2_X1();
  }

  @Override
  public List<Object> apply(final Context context) {
    Object value1 = context.getStack()
                           .peek();
    Object value2 = context.getStack()
                           .peek(1);
    Object value3 = context.getStack()
                           .peek(2);

    return context.getStackPush()
                  .prepare()
                  .push(value2, value1, value3, value2, value1)
                  .get();
  }

  @Override
  public int getStackPopCount() {
    return 3;
  }
}
