package net.andreho.haxxor.cgen.instr.jumps;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.LABEL;
import net.andreho.haxxor.cgen.instr.abstr.AbstractJumpInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class JSR
    extends AbstractJumpInstruction {

  public JSR(LABEL label) {
    super(Opcodes.JSR, label);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.JSR(this.label);
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    super.apply(context);
    return PUSH_INT;
  }
}
