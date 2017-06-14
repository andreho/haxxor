package net.andreho.haxxor.cgen.instr.local.store;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractLocalAccessInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class ISTORE
    extends AbstractLocalAccessInstruction {

  public ISTORE(int var) {
    super(Opcodes.ISTORE, var);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.ISTORE(getLocalIndex());
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    Object operand = context.getStack().pop();
    if (operand != Opcodes.INTEGER) {
      throw new IllegalArgumentException(
          "An int operand is expected at slot index [" + getLocalIndex() + "], but got: " + operand);
    }
    context.getLocals().set(getLocalIndex(), operand);
    return NO_STACK_PUSH;
  }
}
