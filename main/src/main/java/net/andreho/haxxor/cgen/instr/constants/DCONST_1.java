package net.andreho.haxxor.cgen.instr.constants;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class DCONST_1
    extends AbstractZeroOperandInstruction {

  public DCONST_1() {
    super(Opcodes.DCONST_1);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.DCONST_1();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return PUSH_DOUBLE;
  }
}
