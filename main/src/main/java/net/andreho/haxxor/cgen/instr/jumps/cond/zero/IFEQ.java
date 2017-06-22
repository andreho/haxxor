package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * IFEQ succeeds if and only if value = 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFEQ
  extends AbstractSimpleJumpInstruction {

  public IFEQ(LABEL label) {
    super(Opcodes.IFEQ, label);
  }

  @Override
  public IFEQ clone(final LABEL label) {
    return new IFEQ(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IFEQ(this.label);
  }
}
