package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.CodeStream;
import net.andreho.haxxor.cgen.Context;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFNONNULL
    extends AbstractJumpInstruction {

  public IFNONNULL(LABEL label) {
    super(Opcodes.IFNONNULL, label);
  }

  @Override
  public void dumpTo(Context context, CodeStream codeStream) {
    codeStream.IFNONNULL(this.label);
  }

  @Override
  public int getStackPopCount() {
    return 1;
  }
}
