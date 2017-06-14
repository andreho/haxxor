package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Pop the top one or two operand stack values.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class POP2
    extends AbstractZeroOperandInstruction {

  public POP2() {
    super(Opcodes.POP2);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.POP2();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }

}
