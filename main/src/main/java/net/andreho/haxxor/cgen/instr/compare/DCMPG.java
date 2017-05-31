package net.andreho.haxxor.cgen.instr.compare;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class DCMPG
    extends AbstractZeroOperandInstruction {

  public DCMPG() {
    super(Opcodes.DCMPG);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.DCMPG();
  }

  @Override
  public List<Object> apply(final Context context) {
    return PUSH_INT;
  }

  @Override
  public int getStackPopCount() {
    return 2 + 2;
  }
}
