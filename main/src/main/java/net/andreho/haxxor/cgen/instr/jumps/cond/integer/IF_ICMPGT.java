package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPGT
  extends AbstractSimpleJumpInstruction {

  public IF_ICMPGT(LABEL label) {
    super(Opcodes.IF_ICMPGT, label);
  }

  @Override
  public IF_ICMPGT clone(final LABEL label) {
    return new IF_ICMPGT(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ICMPGT(this.label);
  }
}
