package net.andreho.haxxor.cgen.instr.conversion.primitive;

import net.andreho.asm.org.objectweb.asm.Opcodes;
import net.andreho.haxxor.cgen.HxCodeStream;
import net.andreho.haxxor.cgen.HxComputingContext;
import net.andreho.haxxor.cgen.instr.abstr.AbstractZeroOperandInstruction;

import java.util.List;

/**
 * <br/>Created by a.hofmann on 09.03.2016.<br/>
 */
public class D2F
    extends AbstractZeroOperandInstruction {

  public D2F() {
    super(Opcodes.D2F);
  }

  @Override
  public void visit(HxCodeStream codeStream) {
    codeStream.D2F();
  }

  @Override
  public List<Object> getStackPushList(final HxComputingContext context) {
    return PUSH_FLOAT;
  }
}
