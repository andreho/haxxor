package net.andreho.haxxor.cgen.instr.jumps;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class GOTO
  extends AbstractSimpleJumpInstruction {

  public GOTO(LABEL label) {
    super(Opcodes.GOTO, label);
  }

  @Override
  public GOTO clone(final LABEL label) {
    return new GOTO(label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.GOTO(this.label);
  }
}
