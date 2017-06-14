package net.andreho.haxxor.cgen.instr;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 03.03.2016.<br/>
 */
public class NOP
    extends AbstractZeroOperandInstruction {

  public NOP() {
    super(Opcodes.NOP);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.NOP();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return NO_STACK_PUSH;
  }
}
