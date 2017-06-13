package net.andreho.haxxor.cgen.instr.binary;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class LSHL
    extends AbstractZeroOperandInstruction {

  public LSHL() {
    super(Opcodes.LSHL);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.LSHL();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_LONG;
  }
}
