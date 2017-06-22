package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPNE
  extends AbstractSimpleJumpInstruction {

  public IF_ICMPNE(LABEL label) {
    super(Opcodes.IF_ICMPNE, label);
  }

  @Override
  public IF_ICMPNE clone(final LABEL label) {
    return new IF_ICMPNE(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ICMPNE(this.label);
  }
}
