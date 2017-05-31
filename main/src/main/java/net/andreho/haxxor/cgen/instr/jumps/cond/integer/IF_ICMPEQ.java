package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPEQ
    extends AbstractJumpInstruction {

  public IF_ICMPEQ(LABEL label) {
    super(Opcodes.IF_ICMPEQ, label);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.IF_ICMPEQ(this.label);
  }

  @Override
  public List<Object> apply(final Context context) {
    super.apply(context);
    return PUSH_INT;
  }

  @Override
  public int getStackPopCount() {
    return 1 + 1;
  }
}
