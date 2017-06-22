package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * IFLT succeeds if and only if value < 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFLT
  extends AbstractSimpleJumpInstruction {

  public IFLT(LABEL label) {
    super(Opcodes.IFLT, label);
  }

  @Override
  public IFLT clone(final LABEL label) {
    return new IFLT(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFLT(this.label);
  }
}
