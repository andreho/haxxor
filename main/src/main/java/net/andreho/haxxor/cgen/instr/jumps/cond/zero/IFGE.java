package net.andreho.haxxor.cgen.instr.jumps.cond.zero;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

/**
 * IFGE succeeds if and only if value >= 0<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IFGE
  extends AbstractSimpleJumpInstruction {

  public IFGE(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.Jump.IFGE;
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
