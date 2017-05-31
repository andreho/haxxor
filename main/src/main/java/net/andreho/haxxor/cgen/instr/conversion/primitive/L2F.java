package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class L2F
    extends AbstractZeroOperandInstruction {

  public L2F() {
    super(Opcodes.L2F);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.L2F();
  }

  @Override
  public List<Object> apply(final Context context) {
    return PUSH_FLOAT;
  }

  @Override
  public int getStackPopCount() {
    return 2;
  }
}
