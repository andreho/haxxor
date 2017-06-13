package net.andreho.haxxor.cgen.instr.binary;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IXOR
    extends AbstractZeroOperandInstruction {

  public IXOR() {
    super(Opcodes.IXOR);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IXOR();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_INT;
  }
}
