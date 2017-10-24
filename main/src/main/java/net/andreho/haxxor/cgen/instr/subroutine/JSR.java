package net.andreho.haxxor.cgen.instr.subroutine;

import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.HxInstructionType;
import net.andreho.haxxor.cgen.HxInstructionTypes;
import net.andreho.haxxor.cgen.instr.misc.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractSimpleJumpInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class JSR
  extends AbstractSimpleJumpInstruction {

  public JSR(LABEL label) {
    super(label);
  }

  @Override
  public HxInstructionType getInstructionType() {
    return HxInstructionTypes.SubRoutine.JSR;
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.JSR(this.label);
  }

  @Override
  public JSR clone(final LABEL label) {
    return new JSR(label);
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    super.compute(context);
    return PUSH_INT;
  }
}