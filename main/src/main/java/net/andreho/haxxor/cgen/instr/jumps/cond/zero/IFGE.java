package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * IFGE succeeds if and only if value >= 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFGE
  extends AbstractSimpleJumpInstruction {

  public IFGE(LABEL label) {
    super(Opcodes.IFGE, label);
  }

  @Override
  public IFGE clone(final LABEL label) {
    return new IFGE(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFGE(this.label);
  }
}
