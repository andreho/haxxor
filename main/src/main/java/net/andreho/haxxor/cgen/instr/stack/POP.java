package net.andreho.haxxor.cgen.instr.stack;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * Pop the top operand stack value.<br/>
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class POP
    extends AbstractZeroOperandInstruction {

  public POP() {
    super(Opcodes.POP);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.POP();
  }

  @Override
  public List<Object> compute(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
