package net.andreho.haxxor.cgen.instr.jumps.cond.refs;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFNONNULL
  extends AbstractSimpleJumpInstruction {

  public IFNONNULL(LABEL label) {
    super(Opcodes.IFNONNULL, label);
  }

  @Override
  public IFNONNULL clone(final LABEL label) {
    return new IFNONNULL(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFNONNULL(this.label);
  }
}
