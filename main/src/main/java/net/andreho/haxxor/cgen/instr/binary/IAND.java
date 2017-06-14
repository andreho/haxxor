package net.andreho.haxxor.cgen.instr.binary;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IAND
    extends AbstractZeroOperandInstruction {

  public IAND() {
    super(Opcodes.IAND);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IAND();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_INT;
  }
}
