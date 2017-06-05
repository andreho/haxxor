package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class FCONST_1
    extends AbstractZeroOperandInstruction {

  public FCONST_1() {
    super(Opcodes.FCONST_1);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.FCONST_1();
  }

  @Override
  public List<Object> apply(final Context context) {
    return PUSH_FLOAT;
  }

  @Override
  public int getStackPopCount() {
    return 0;
  }
}