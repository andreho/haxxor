package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFNULL
  extends AbstractSimpleJumpInstruction {

  public IFNULL(LABEL label) {
    super(Opcodes.IFNULL, label);
  }

  @Override
  public IFNULL clone(final LABEL label) {
    return new IFNULL(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFNULL(this.label);
  }
}
