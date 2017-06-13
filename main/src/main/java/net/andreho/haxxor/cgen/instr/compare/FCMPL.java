package net.andreho.haxxor.cgen.instr.compare;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class FCMPL
    extends AbstractZeroOperandInstruction {

  public FCMPL() {
    super(Opcodes.FCMPL);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.FCMPL();
  }

  @Override
  public List<Object> apply(final HxComputingContext context) {
    return PUSH_INT;
  }
}
