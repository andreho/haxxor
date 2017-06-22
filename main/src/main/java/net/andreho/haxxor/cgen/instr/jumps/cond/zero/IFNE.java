package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * IFNE succeeds if and only if value != 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFNE
  extends AbstractSimpleJumpInstruction {

  public IFNE(LABEL label) {
    super(Opcodes.IFNE, label);
  }

  @Override
  public IFNE clone(final LABEL label) {
    return new IFNE(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFNE(this.label);
  }
}
