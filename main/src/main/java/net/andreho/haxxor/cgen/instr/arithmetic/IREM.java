package net.andreho.haxxor.cgen.instr.arithmetic;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class IREM
    extends AbstractZeroOperandInstruction {

  public IREM() {
    super(Opcodes.IREM);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.IREM();
  }

  @Override
  public List<Object> getStackPushList(HxComputingContext context) {
    return PUSH_INT;
  }
}
