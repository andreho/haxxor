package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class F2I
    extends AbstractZeroOperandInstruction {

  public F2I() {
    super(Opcodes.F2I);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.F2I();
  }

  @Override
  public List<Object> apply(final Context context) {
    return PUSH_INT;
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }
}
