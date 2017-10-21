package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class FCONST_0
    extends AbstractZeroOperandInstruction {

  public FCONST_0() {
    super(Opcodes.FCONST_0);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.FCONST_0();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_FLOAT;
  }
}
