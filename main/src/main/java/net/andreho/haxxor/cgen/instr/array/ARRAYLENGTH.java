package net.andreho.haxxor.cgen.instr.array;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class ARRAYLENGTH
    extends AbstractZeroOperandInstruction {

  public ARRAYLENGTH() {
    super(Opcodes.ARRAYLENGTH);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.ARRAYLENGTH();
  }

  @Override
  public List<Object> apply(Context context) {
    Object arrayType = context.getStack()
                              .peek();
    if (arrayType.getClass() != String.class ||
        arrayType.toString()
                 .charAt(0) != '[') {
      throw new IllegalStateException("An array type is expected at stack[stack.length - 1]: " + arrayType);
    }

    return PUSH_INT;
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }
}