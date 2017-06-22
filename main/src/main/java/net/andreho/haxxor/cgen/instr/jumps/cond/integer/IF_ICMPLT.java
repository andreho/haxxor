package net.andreho.haxxor.cgen.instr.jumps.cond.integer;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IF_ICMPLT
  extends AbstractSimpleJumpInstruction {

  public IF_ICMPLT(LABEL label) {
    super(Opcodes.IF_ICMPLT, label);
  }

  @Override
  public IF_ICMPLT clone(final LABEL label) {
    return new IF_ICMPLT(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IF_ICMPLT(this.label);
  }
}
