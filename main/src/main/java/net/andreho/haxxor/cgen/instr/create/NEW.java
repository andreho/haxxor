package net.andreho.haxxor.cgen.instr.create;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractStringOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 10.03.2016.<br/>
 */
public class NEW
    extends AbstractStringOperandInstruction {

  public NEW(String className) {
    super(Opcodes.NEW, className);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NEW(getOperand());
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_UNINITIALIZED_THIS;
  }

  @Override
  public String toString() {
    return super.toString() + " " + getOperand();
  }
}
